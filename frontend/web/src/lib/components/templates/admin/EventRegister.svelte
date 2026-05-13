<script lang="ts">
	import type { IRegisterEventRequest } from '$lib/api/commands/events/register-event/register-event.interface';
	import Button from '$lib/components/atoms/Button.svelte';
	import EventForm from '$lib/components/molecules/forms/EventForm.svelte';
	import GoBack from '$lib/components/molecules/GoBack.svelte';
	import { toast } from 'svoast';
	import type { IBoardGame } from '$lib/api/queries/board-games/list-board-games/list-board-games.interface';

	export let isLoading: boolean;
	export let onCreateEvent: (data: IRegisterEventRequest) => void;
	export let boardGames: IBoardGame[];

	let isClicked: boolean = false;
	let formIsValid: boolean = false;
	let formValues: Record<keyof IRegisterEventRequest, any> = {
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
	};

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
	<EventForm bind:isValid={formIsValid} {formValues} {boardGames} {isClicked} />
	<Button
		text="Criar Evento"
		width="290px"
		height="40px"
		onClick={handleOnCreate}
		loading={isLoading}
	/>
</main>
