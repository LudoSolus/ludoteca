<script lang="ts">
	import { goto } from '$app/navigation';
	import { page } from '$app/stores';
	import { LoanGameCommand } from '$lib/api/commands/board-games/loan-game/loan-game.command';
	import type { ILoanGameRequest } from '$lib/api/commands/board-games/loan-game/loan-game.interface';
	import { ReturnGameCommand } from '$lib/api/commands/board-games/return-game/return-game.command';
	import { DeleteEventCommand } from '$lib/api/commands/events/delete-event/delete-user.command';
	import { FinishEventCommand } from '$lib/api/commands/events/finish-event/finish-event.command';
	import { RegisterParticipationInEventCommand } from '$lib/api/commands/events/register-participation-in-event/register-participation-in-event.query';
	import { StartEventCommand } from '$lib/api/commands/events/start-event/start-event.command';
	import type {
		GameDetailsForEvent,
		GetEventDetailsResponse
	} from '$lib/api/queries/events/get-event-details/get-event-details.interface';
	import { GetEventDetailsQuery } from '$lib/api/queries/events/get-event-details/get-event-details.query';
	import ConfirmationModal from '$lib/components/molecules/ConfirmationModal.svelte';
	import EventRegisterUserModal from '$lib/components/molecules/EventRegisterUserModal.svelte';
	import LoanGameModal from '$lib/components/molecules/LoanGameModal.svelte';
	import ReturnGameModal from '$lib/components/molecules/ReturnGameModal.svelte';
	import EventDetails from '$lib/components/templates/EventDetails.svelte';
	import { EEventStatus } from '$lib/shared/enums/event-status.enum';
	import { CommandsHandlerService } from '$lib/shared/handlers/command/commands-handler.service';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import axios from 'axios';
	import { onMount } from 'svelte';
	import { toast } from 'svoast';

	const queriesHandler = new QueriesHandlerService(axios);
	const commandsHandler = new CommandsHandlerService(axios);

	let eventData: GetEventDetailsResponse | null = null;
	let selectedGame: GameDetailsForEvent | null = null;

	let eventRegisterUserModalIsOpen: boolean = false;
	let eventRegisterUserLoading: boolean = false;

	let loanGameModalIsOpen: boolean = false;
	let loanGameLoading: boolean = false;

	let returnGameModalIsOpen: boolean = false;
	let returnGameLoading: boolean = false;

	let confirmStartEventModalIsOpen: boolean = false;
	let confirmStartEventIsLoading: boolean = false;

	let confirmFinishEventModalIsOpen: boolean = false;
	let confirmFinishEventisLoading: boolean = false;

	let isLoadingDelete: boolean = false;

	onMount(() => {
		fetchEvent();
	});

	// Função que utilizam as queries e commands

	function fetchEvent() {
		const eventId = $page.params.id;
		if (!eventId) return;

		queriesHandler.handle(new GetEventDetailsQuery(eventId)).subscribe({
			next: (res) => {
				eventData = res.resultData;
			}
		});
	}

	function registerParticipationInEvent(userPublicId: string) {
		eventRegisterUserLoading = true;
		const eventId = $page.params.id;
		if (!eventId) return;
		commandsHandler
			.handle(new RegisterParticipationInEventCommand(userPublicId, eventId))
			.subscribe({
				next: (res) => {
					toast.success((res as any).resultData, { closable: true });
					eventRegisterUserModalIsOpen = false;
					eventRegisterUserLoading = false;
				},
				error: (err) => {
					eventRegisterUserLoading = false;
				}
			});
	}

	function startEvent() {
		confirmStartEventIsLoading = true;
		const eventId = $page.params.id;
		if (!eventId) return;
		commandsHandler.handle(new StartEventCommand(eventId)).subscribe({
			next: (res) => {
				toast.success((res as any).data.resultData ?? 'Evento iniciado com sucesso!', {
					closable: true
				});
				confirmStartEventModalIsOpen = false;
				confirmStartEventIsLoading = false;
				fetchEvent();
			},
			error: (err) => {
				confirmStartEventIsLoading = false;
			}
		});
	}

	function finishEvent() {
		confirmFinishEventisLoading = true;
		const eventId = $page.params.id;
		if (!eventId) return;
		commandsHandler.handle(new FinishEventCommand(eventId)).subscribe({
			next: (res) => {
				toast.success((res as any).data.resultData ?? 'Evento finalizado com sucesso!', {
					closable: true
				});
				confirmFinishEventModalIsOpen = false;
				confirmFinishEventisLoading = false;
				fetchEvent();
			},
			error: (err) => {
				toast.error(err, { closable: true });
				confirmFinishEventisLoading = false;
			}
		});
	}

	function loanGame(userPublicId: string) {
		loanGameLoading = true;
		const eventId = $page.params.id;
		if (!eventId || !selectedGame) return;

		const body: ILoanGameRequest = {
			userPublicId: userPublicId,
			gameId: selectedGame?.id,
			eventId: eventId
		};

		commandsHandler.handle(new LoanGameCommand(body)).subscribe({
			next: (res) => {
				toast.success((res as any).data.resultData ?? 'Empréstimo realizado com sucesso!', {
					closable: true
				});
				closeLoanGameModal();
				loanGameLoading = false;
				fetchEvent();
			},
			error: (err) => {
				loanGameLoading = false;
			}
		});
	}

	function returnGame(gameId: string) {
		returnGameLoading = true;

		commandsHandler.handle(new ReturnGameCommand(gameId)).subscribe({
			next: (res) => {
				toast.success((res as any).data.resultData ?? 'Jogo devolvido com sucesso!', {
					closable: true
				});
				closeReturnGameModal();
				returnGameLoading = false;
				fetchEvent();
			},
			error: (err) => {
				returnGameLoading = false;
			}
		});
	}

	function deleteEvent() {
		const eventId = $page.params.id;
		if (!eventId) return;
		isLoadingDelete = true;

		commandsHandler.handle(new DeleteEventCommand(eventId)).subscribe({
			next: (res) => {
				toast.success('Evento deletado com sucesso!', { closable: true });
				isLoadingDelete = false;
				goto('/admin/events');
			},
			error: (err) => {
				isLoadingDelete = false;
			}
		});
	}

	// Funções para gerenciamento dos modais

	function openRegisterUserModal() {
		eventRegisterUserModalIsOpen = true;
	}

	function handleOnClickLoanGame(gameId: string) {
		if (eventData?.status == EEventStatus.SCHEDULED) {
			toast.info('O evento ainda não começou, não é possível emprestar jogos.');
			return;
		}
		if (eventData?.status == EEventStatus.COMPLETED) {
			toast.info('O evento já foi finalizado, não é possível emprestar jogos.');
			return;
		}

		const game = eventData?.listGames.find((g) => g.id == gameId);
		if (!game) {
			toast.error('Jogo escolhido não encontrado, reinicie a página!', { closable: true });
			return;
		}

		selectedGame = game;
		if (!game.isAvailable) {
			returnGameModalIsOpen = true;
		} else {
			loanGameModalIsOpen = true;
		}
	}

	function openReturnGameWithoutGame() {
		selectedGame = null;
		returnGameModalIsOpen = true;
	}

	function closeLoanGameModal() {
		selectedGame = null;
		loanGameModalIsOpen = false;
	}

	function closeReturnGameModal() {
		selectedGame = null;
		returnGameModalIsOpen = false;
	}

	function openConfirmStartEventModal() {
		confirmStartEventModalIsOpen = true;
	}

	function openConfirmFinishEventModal() {
		confirmFinishEventModalIsOpen = true;
	}

	function handleOnEdit() {
		const eventId = $page.params.id;
		if (!eventId) return;

		goto(`/admin/events/${eventId}/edit`);
	}

	function goToGameDetails(gameId: string) {
		goto(`/admin/board-games/${gameId}`);
	}
</script>

<EventDetails
	{eventData}
	openRegisterUser={openRegisterUserModal}
	loanGame={handleOnClickLoanGame}
	startEvent={openConfirmStartEventModal}
	finishEvent={openConfirmFinishEventModal}
	returnGame={openReturnGameWithoutGame}
	{handleOnEdit}
	handleOnDelete={deleteEvent}
	goToGameDetails={goToGameDetails}
	{isLoadingDelete}
	type="admin"
/>

<EventRegisterUserModal
	bind:isOpen={eventRegisterUserModalIsOpen}
	isLoading={eventRegisterUserLoading}
	onEventRegisterUser={registerParticipationInEvent}
/>

<ConfirmationModal
	bind:isOpen={confirmStartEventModalIsOpen}
	isLoading={confirmStartEventIsLoading}
	onConfirm={startEvent}
	text="Tem certeza que deseja iniciar o evento?"
	subTitle="Depois não será possível editar as suas informações."
	confirmButtonText="Iniciar"
/>

<ConfirmationModal
	bind:isOpen={confirmFinishEventModalIsOpen}
	isLoading={confirmFinishEventisLoading}
	onConfirm={finishEvent}
	text="Tem certeza que deseja finalizar o evento?"
	confirmButtonText="Finalizar"
/>

<ReturnGameModal
	bind:isOpen={returnGameModalIsOpen}
	isLoading={returnGameLoading}
	onReturnGame={(gameId) => returnGame(gameId)}
	game={selectedGame
		? {
				...selectedGame,
				title: selectedGame.nameGame
			}
		: null}
/>

{#if selectedGame}
	<LoanGameModal
		bind:isOpen={loanGameModalIsOpen}
		isLoading={loanGameLoading}
		onLoanGame={(publicId) => loanGame(publicId)}
		gameName={selectedGame.nameGame}
	/>
{/if}
