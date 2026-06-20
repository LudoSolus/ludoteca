<script lang="ts">
	import type { IUpdateEventRequest } from '$lib/api/commands/events/update-event/update-event.interface';
	import type { IBoardGame } from '$lib/api/queries/board-games/list-board-games/list-board-games.interface';
	import type { GetEventDetailsResponse } from '$lib/api/queries/events/get-event-details/get-event-details.interface';
	import Button from '$lib/components/atoms/Button.svelte';
	import EventForm from '$lib/components/molecules/forms/EventForm.svelte';
	import GoBack from '$lib/components/molecules/GoBack.svelte';
	import { formatDateForInput } from '$lib/shared/helpers/format-date-for-input';
	import { toast } from 'svoast';

	let {
		event = null,
		isLoading,
		boardGames,
		onEditEvent
	} = $props<{
		event: GetEventDetailsResponse | null;
		isLoading: boolean;
		boardGames: IBoardGame[];
		onEditEvent: (data: IUpdateEventRequest) => void;
	}>();

	let isClicked = $state(false);
	let formIsValid = $state(false);
	let formValues = $state<Record<keyof IUpdateEventRequest, any>>({
		name: '',
		description: '',
		startDate: '',
		finalDate: '',
		street: '',
		number: '',
		supplement: '',
		neighborhood: '',
		city: '',
		state: '',
		zipCode: '',
		hasBoardGames: false,
		hasRpg: false,
		hasEscapeRoom: false,
		gamesIds: []
	});

	$effect(() => {
		if (event) {
			formValues = {
				name: event.name,
				description: event.description,
				startDate: formatDateForInput(event.startDate),
				finalDate: formatDateForInput(event.finalDate),
				street: event.street,
				number: event.number,
				supplement: event.supplement,
				neighborhood: event.neighborhood,
				city: event.city,
				state: event.state,
				zipCode: event.zipCode,
				hasBoardGames: event.hasBoardGames,
				hasRpg: event.hasRpg,
				hasEscapeRoom: event.hasEscapeRoom,
				gamesIds: event.listGames.map((g: any) => g.id)
			};
		}
	});

	function handleOnEdit() {
		isClicked = true;

		if (!formIsValid) {
			toast.error('Preencha todos os campos devidamente.', { closable: true });
			return;
		}

		const formValuesFormated: IUpdateEventRequest = {
			...formValues,
			startDate: new Date(formValues.startDate),
			finalDate: new Date(formValues.finalDate),
			hasBoardGames: formValues.hasBoardGames,
			hasRpg: formValues.hasRpg,
			hasEscapeRoom: formValues.hasEscapeRoom,
			gamesIds: formValues.gamesIds
		};

		onEditEvent(formValuesFormated);
	}
</script>

<main class="flex w-full flex-col items-center gap-5 px-3 py-7 sm:px-10 md:gap-10 xl:px-15">
	<GoBack title="Editar Evento" description="Edite os dados do evento" />
	{#if event === null}
		<div
			class="grid w-full max-w-230 grid-cols-1 place-items-center gap-0 sm:grid-cols-2 sm:gap-3 xl:gap-5"
		>
			<div class="flex w-full flex-col gap-2 sm:col-span-2" style="height: 90px;">
				<div class="skeleton-pulse h-4 w-28 rounded bg-gray-300"></div>
				<div class="skeleton-pulse h-10 w-full rounded border border-gray-200 bg-gray-100"></div>
			</div>

			<div class="flex w-full justify-between gap-3 sm:col-span-2 xl:gap-5">
				<div class="flex w-full flex-col gap-2" style="height: 90px;">
					<div class="skeleton-pulse h-4 w-28 rounded bg-gray-300"></div>
					<div class="skeleton-pulse h-10 w-full rounded border border-gray-200 bg-gray-100"></div>
				</div>
				<div class="flex w-full flex-col gap-2" style="height: 90px;">
					<div class="skeleton-pulse h-4 w-28 rounded bg-gray-300"></div>
					<div class="skeleton-pulse h-10 w-full rounded border border-gray-200 bg-gray-100"></div>
				</div>
			</div>

			<div class="flex w-full flex-col gap-2 sm:col-span-2">
				<div class="skeleton-pulse h-4 w-28 rounded bg-gray-300"></div>
				<div class="skeleton-pulse h-24 w-full rounded border border-gray-200 bg-gray-100"></div>
			</div>

			<div class="flex w-full flex-col gap-2" style="height: 90px;">
				<div class="skeleton-pulse h-4 w-28 rounded bg-gray-300"></div>
				<div class="skeleton-pulse h-10 w-full rounded border border-gray-200 bg-gray-100"></div>
			</div>

			<div class="flex w-full flex-col gap-2" style="height: 90px;">
				<div class="skeleton-pulse h-4 w-28 rounded bg-gray-300"></div>
				<div class="skeleton-pulse h-10 w-full rounded border border-gray-200 bg-gray-100"></div>
			</div>

			<div class="flex w-full justify-between gap-3 xl:gap-5">
				<div class="flex w-full flex-col gap-2" style="height: 90px;">
					<div class="skeleton-pulse h-4 w-28 rounded bg-gray-300"></div>
					<div class="skeleton-pulse h-10 w-full rounded border border-gray-200 bg-gray-100"></div>
				</div>
				<div class="flex w-full flex-col gap-2" style="height: 90px;">
					<div class="skeleton-pulse h-4 w-28 rounded bg-gray-300"></div>
					<div class="skeleton-pulse h-10 w-full rounded border border-gray-200 bg-gray-100"></div>
				</div>
			</div>

			<div class="flex w-full flex-col gap-2" style="height: 90px;">
				<div class="skeleton-pulse h-4 w-28 rounded bg-gray-300"></div>
				<div class="skeleton-pulse h-10 w-full rounded border border-gray-200 bg-gray-100"></div>
			</div>

			<div class="flex w-full justify-between gap-3 xl:gap-5">
				<div class="flex w-full flex-col gap-2" style="height: 90px;">
					<div class="skeleton-pulse h-4 w-28 rounded bg-gray-300"></div>
					<div class="skeleton-pulse h-10 w-full rounded border border-gray-200 bg-gray-100"></div>
				</div>
				<div class="flex w-full flex-col gap-2" style="height: 90px;">
					<div class="skeleton-pulse h-4 w-28 rounded bg-gray-300"></div>
					<div class="skeleton-pulse h-10 w-full rounded border border-gray-200 bg-gray-100"></div>
				</div>
			</div>

			<div class="flex w-full flex-col gap-3 py-4 sm:col-span-2">
				<div class="skeleton-pulse h-5 w-40 rounded bg-gray-300"></div>
				<div class="flex flex-wrap gap-4">
					{#each Array(3) as _}
						<div class="flex items-center gap-2">
							<div class="skeleton-pulse h-5 w-5 rounded border border-gray-200 bg-gray-100"></div>
							<div class="skeleton-pulse h-4 w-24 rounded bg-gray-300"></div>
						</div>
					{/each}
				</div>
			</div>
		</div>
		<div class="skeleton-pulse mt-5 h-10 w-[290px] rounded bg-gray-300"></div>
	{:else}
		<EventForm bind:isValid={formIsValid} bind:formValues={formValues} {isClicked} {boardGames} />
		<Button text="Salvar" width="290px" height="40px" onClick={handleOnEdit} loading={isLoading} />
	{/if}
</main>

<style>
	.skeleton-pulse {
		animation: pulse 1.5s cubic-bezier(0.4, 0, 0.6, 1) infinite;
	}

	@keyframes pulse {
		0%, 100% {
			opacity: 1;
		}
		50% {
			opacity: 0.4;
		}
	}
</style>
