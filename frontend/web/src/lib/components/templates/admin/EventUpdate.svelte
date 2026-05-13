<script lang="ts">
	import type { IUpdateEventRequest } from '$lib/api/commands/events/update-event/update-event.interface';
	import type { IBoardGame } from '$lib/api/queries/board-games/list-board-games/list-board-games.interface';
	import type { GetEventDetailsResponse } from '$lib/api/queries/events/get-event-details/get-event-details.interface';
	import Button from '$lib/components/atoms/Button.svelte';
	import EventForm from '$lib/components/molecules/forms/EventForm.svelte';
	import GoBack from '$lib/components/molecules/GoBack.svelte';
	import { formatDateForInput } from '$lib/shared/helpers/format-date-for-input';
	import { toast } from 'svoast';

	export let event: GetEventDetailsResponse;
	export let isLoading: boolean;
	export let boardGames: IBoardGame[];
	export let onEditEvent: (data: IUpdateEventRequest) => void;

	let isClicked: boolean = false;
	let formIsValid: boolean = false;
	let formValues: Record<keyof IUpdateEventRequest, any> = {
		name: event.name,
		description: '',
		startDate: formatDateForInput(event.startDate),
		finalDate: formatDateForInput(event.finalDate),
		street: event.street,
		number: event.number,
		supplement: event.supplement,
		neighborhood: event.neighborhood,
		city: event.city,
		state: event.state,
		zipCode: event.zipCode,
		hasBoardGames: true,
		hasRpg: true,
		hasEscapeRoom: true,
		gamesIds: event.listGames.map((g) => g.id)
	};

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
	<EventForm bind:isValid={formIsValid} {formValues} {isClicked} {boardGames} />
	<Button text="Salvar" width="290px" height="40px" onClick={handleOnEdit} loading={isLoading} />
</main>
