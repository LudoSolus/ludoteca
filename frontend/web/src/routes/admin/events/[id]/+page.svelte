<script lang="ts">
	import { page } from '$app/stores';
	import { LoanGameCommand } from '$lib/api/commands/board-games/loan-game/loan-game.command';
	import type { ILoanGameRequest } from '$lib/api/commands/board-games/loan-game/loan-game.interface';
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
	import AdminEventDetails from '$lib/components/templates/admin/AdminEventDetails.svelte';
	import { CommandsHandlerService } from '$lib/shared/handlers/command/commands-handler.service';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import axios from 'axios';
	import { onMount } from 'svelte';
	import { toast } from 'svoast';

	const queriesHandler = new QueriesHandlerService(axios);
	const commandsHandler = new CommandsHandlerService(axios);

	let eventData: GetEventDetailsResponse | null = null;

	let eventRegisterUserModalIsOpen: boolean = false;
	let eventRegisterUserLoading: boolean = false;

	let loanGameModalIsOpen: boolean = false;
	let loanGameLoading: boolean = false;
	let selectedGame: GameDetailsForEvent | null = null;

	let confirmStartEventModalIsOpen: boolean = false;
	let confirmStartEventIsLoading: boolean = false;

	let confirmFinishEventModalIsOpen: boolean = false;
	let confirmFinishEventisLoading: boolean = false;

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
			},
			error: (err) => {
				console.log(err);
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
			},
			error: (err) => {
				loanGameLoading = false;
			}
		});
	}

	// Funções para gerenciamento dos modais

	function openRegisterUserModal() {
		eventRegisterUserModalIsOpen = true;
	}

	function handleOnLoanGame(gameId: string) {
		const game = eventData?.listGames.find((g) => g.id == gameId);
		if (!game) {
			toast.error('Jogo escolhido não encontrado, reinicie a página!', { closable: true });
			return;
		}

		if (!game.isAvailable) {
			toast.error('O jogo já está emprestado!', { closable: true });
			return;
		}
		selectedGame = game;
		loanGameModalIsOpen = true;
	}

	function closeLoanGameModal() {
		selectedGame = null;
		loanGameModalIsOpen = false;
	}

	function openConfirmStartEventModal() {
		confirmStartEventModalIsOpen = true;
	}

	function openConfirmFinishEventModal() {
		confirmFinishEventModalIsOpen = true;
	}
</script>

{#if eventData}
	<AdminEventDetails
		{eventData}
		openRegisterUser={openRegisterUserModal}
		loanGame={handleOnLoanGame}
		startEvent={openConfirmStartEventModal}
		finishEvent={openConfirmFinishEventModal}
	/>
{:else}
	<p>Carregando...</p>
{/if}

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

{#if selectedGame}
	<LoanGameModal
		bind:isOpen={loanGameModalIsOpen}
		isLoading={loanGameLoading}
		onLoanGame={(publicId) => loanGame(publicId)}
		gameName={selectedGame.nameGame}
	/>
{/if}
