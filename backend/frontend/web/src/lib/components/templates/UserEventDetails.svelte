<script lang="ts">
	import Fa from 'svelte-fa';
	import GoBack from '../molecules/GoBack.svelte';
	import { faCalendar, faLocationDot } from '@fortawesome/free-solid-svg-icons';
	import { formatCEP } from '$lib/shared/helpers/format-cep';
	import { formatDate } from '$lib/shared/helpers/format-date';
	import { formatTime } from '$lib/shared/helpers/format-time';
	import type { GetEventDetailsResponse } from '$lib/api/queries/events/get-event-details/get-event-details.interface';
	import BoardGamesList from '../organisms/BoardGamesList.svelte';

	export let eventData: GetEventDetailsResponse;
</script>

<main class="flex h-full flex-col items-center gap-10 overflow-y-auto px-1 py-7 sm:px-10 xl:px-15">
	<GoBack title={eventData.name} description="Detalhes do evento" />
	<div
		class="flex w-full max-w-300 flex-wrap items-start justify-between gap-10 px-0 pb-20 sm:px-2 lg:px-4 xl:px-10"
	>
		<div class="flex flex-1 flex-col gap-8">
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
			/>
		</div>
	</div>
</main>
