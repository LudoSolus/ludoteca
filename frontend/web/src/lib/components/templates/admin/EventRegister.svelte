<script lang="ts">
	import type { IRegisterEventRequest } from '$lib/api/commands/events/register-event/register-event.interface';
	import Button from '$lib/components/atoms/Button.svelte';
	import EventForm from '$lib/components/molecules/forms/EventForm.svelte';
	import GoBack from '$lib/components/molecules/GoBack.svelte';
	import { toast } from 'svoast';
	import type { IBoardGame } from '$lib/api/queries/board-games/list-board-games/list-board-games.interface';
	import type { GetEventDetailsResponse } from '$lib/api/queries/events/get-event-details/get-event-details.interface';
	import { formatDateForInput } from '$lib/shared/helpers/format-date-for-input';

	export let isLoading: boolean;
	export let onCreateEvent: (data: IRegisterEventRequest) => void;
	export let boardGames: IBoardGame[];
	export let eventForCopy: GetEventDetailsResponse | null = null;

	let isClicked: boolean = false;
	let formIsValid: boolean = false;
	let formValues: Record<keyof IRegisterEventRequest, any> = {
		name: eventForCopy ? `Cópia - ${eventForCopy.name}` : '',
		description: eventForCopy?.description ?? '',
		startDate: eventForCopy?.startDate ? formatDateForInput(eventForCopy.startDate) : '',
		finalDate: eventForCopy?.finalDate ? formatDateForInput(eventForCopy.finalDate) : '',
		street: eventForCopy?.street ?? '',
		number: eventForCopy?.number ?? '',
		supplement: eventForCopy?.supplement ?? '',
		neighborhood: eventForCopy?.neighborhood ?? '',
		city: eventForCopy?.city ?? '',
		state: eventForCopy?.state ?? '',
		zipCode: eventForCopy?.zipCode ?? '',
		hasBoardGames: eventForCopy?.hasBoardGames ?? false,
		hasRpg: eventForCopy?.hasRpg ?? false,
		hasEscapeRoom: eventForCopy?.hasEscapeRoom ?? false,
		gamesIds: eventForCopy?.listGames.map((game) => game.id) ?? []
	};

	$: if (eventForCopy) {
		formValues = {
			name: `Cópia - ${eventForCopy.name}`,
			description: eventForCopy.description ?? '',
			startDate: eventForCopy.startDate ? formatDateForInput(eventForCopy.startDate) : '',
			finalDate: eventForCopy.finalDate ? formatDateForInput(eventForCopy.finalDate) : '',
			street: eventForCopy.street ?? '',
			number: eventForCopy.number ?? '',
			supplement: eventForCopy.supplement ?? '',
			neighborhood: eventForCopy.neighborhood ?? '',
			city: eventForCopy.city ?? '',
			state: eventForCopy.state ?? '',
			zipCode: eventForCopy.zipCode ?? '',
			hasBoardGames: eventForCopy.hasBoardGames ?? false,
			hasRpg: eventForCopy.hasRpg ?? false,
			hasEscapeRoom: eventForCopy.hasEscapeRoom ?? false,
			gamesIds: eventForCopy.listGames?.map((game) => game.id) ?? []
		};
	}

	function handleOnCreate() {
		isClicked = true;

		if (!formIsValid) {
			toast.error('Preencha todos os campos devidamente.', { closable: true });
			return;
		}

		const formValuesFormated: IRegisterEventRequest = {
			...formValues,
			startDate: new Date(formValues.startDate),
			finalDate: new Date(formValues.finalDate),
			hasBoardGames: formValues.hasBoardGames,
			hasRpg: formValues.hasRpg,
			hasEscapeRoom: formValues.hasEscapeRoom,
			gamesIds: formValues.gamesIds
		};

		onCreateEvent(formValuesFormated);
	}
</script>

<main class="flex w-full flex-col items-center gap-5 px-3 py-7 sm:px-10 md:gap-10 xl:px-15">
	<GoBack title="Criar Evento" description="Crie o próximo evento" />
	<EventForm bind:isValid={formIsValid} bind:formValues {boardGames} {isClicked} />
	<Button
		text="Criar Evento"
		width="290px"
		height="40px"
		onClick={handleOnCreate}
		loading={isLoading}
	/>
</main>
