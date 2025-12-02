<script lang="ts">
	import Fa from 'svelte-fa';
	import { faCalendar, faLocationDot, faUser } from '@fortawesome/free-solid-svg-icons';
	import { formatCEP } from '$lib/shared/helpers/format-cep';
	import { formatDate } from '$lib/shared/helpers/format-date';
	import { formatTime } from '$lib/shared/helpers/format-time';
	import type { GetEventDetailsResponse } from '$lib/api/queries/events/get-event-details/get-event-details.interface';
	import GoBack from '$lib/components/molecules/GoBack.svelte';
	import Button from '$lib/components/atoms/Button.svelte';
	import BoardGamesList from '$lib/components/organisms/BoardGamesList.svelte';
	import { EEventStatus } from '$lib/shared/enums/event-status.enum';

	export let eventData: GetEventDetailsResponse;
	export let openRegisterUser: () => void;
	export let loanGame: (gameId: string) => void;
	export let startEvent: () => void;
	export let finishEvent: () => void;
	export let returnGame: () => void;

	const today = new Date();
	$: eventStartToday = eventData.startDate == today;
</script>

<main class="flex h-full flex-col items-center gap-10 px-1 py-7 sm:px-10 xl:px-15">
	<GoBack
		title={eventData.name}
		description="Detalhes do evento"
		onDelete={eventData.status == EEventStatus.SCHEDULED ? () => {} : null}
		onEdit={eventData.status == EEventStatus.SCHEDULED ? () => {} : null}
	/>
	<div
		class="flex w-full max-w-350 flex-wrap items-start justify-between gap-10 px-0 pb-20 sm:px-2 lg:px-4 xl:px-10"
	>
		<div class="flex flex-1 flex-col gap-14">
			<div class="flex flex-col gap-8">
				<h3 class="h3">Detalhes</h3>
				<div class="flex gap-4">
					<Fa icon={faLocationDot} size="2x" />
					<div>
						<p>{eventData.street}, n° {eventData.number}</p>
						<p>Bairro: {eventData.neighborhood}</p>
						<p>{eventData.city}, {eventData.state}</p>
						<p>{formatCEP(eventData.zipCode)}</p>
					</div>
				</div>
				<div class="flex gap-4">
					<Fa icon={faCalendar} size="2x" />
					<div class="flex min-w-50 flex-col gap-1">
						<p>
							<span class="font-bold">Começo:{' '}</span>{formatDate(eventData.startDate)} - {formatTime(
								eventData.startDate
							)}
						</p>
						<p>
							<span class="font-bold">Término:{' '}</span>{formatDate(eventData.finalDate)} - {formatTime(
								eventData.finalDate
							)}
						</p>
					</div>
				</div>
			</div>
			<div class="flex w-full flex-col items-center gap-4">
				{#if eventData.status == EEventStatus.SCHEDULED}
					<Button text="Iniciar Evento" onClick={startEvent} />
				{:else if eventData.status == EEventStatus.INPROGRESS}
					<Button text="Finalizar Evento" onClick={finishEvent} />
					<Button text="Registrar Usuário" leftIcon={faUser} onClick={openRegisterUser} />
					<Button text="Devolver jogo" onClick={returnGame} />
				{/if}
			</div>
		</div>
		<div class="flex flex-1 items-center justify-center">
			<BoardGamesList
				gamesList={eventData.listGames.map((game) => ({
					id: game.id,
					barcode: game.barcode,
					name: game.nameGame,
					category: game.category,
					minParticipants: game.minPlayers,
					maxParticipants: game.maxPlayers,
					isAvailable: game.isAvailable
				}))}
				onClickGame={(gameId) => {}}
				onClickLoanGame={eventData.status == EEventStatus.INPROGRESS ? loanGame : null}
			/>
		</div>
	</div>
</main>
