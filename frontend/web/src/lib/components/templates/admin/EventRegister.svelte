<script lang="ts">
	import type { IRegisterEventRequest } from '$lib/api/commands/events/register-event/register-event.interface';
	import Button from '$lib/components/atoms/Button.svelte';
	import GoBack from '$lib/components/molecules/GoBack.svelte';
	import { toast } from 'svoast';

	export let isLoading: boolean;
	export let onCreateEvent: (data: IRegisterEventRequest) => void;

	let formIsValid: boolean = false;
	let formValues: Record<keyof IRegisterEventRequest, string> = {
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
		hasBoardGames: '',
		hasRpg: '',
		hasEscapeRoom: '',
		gamesIds: ''
	};

	function handleOnCreate() {
		if (!formIsValid) {
			toast.error('Preencha todos os campos devidamente.', { closable: true });
			return;
		}

		// const formValuesFormated: IRegisterEventRequest = {
		// 	...formValues
		// 	// barcode: Number(formValues.barcode.replace(/\D+/g, '')),
		// 	// minPlayers: Number(formValues.minPlayers.replace(/\D+/g, '')),
		// 	// maxPlayers: Number(formValues.maxPlayers.replace(/\D+/g, ''))
		// };

		// onCreateEvent(formValuesFormated);
	}
</script>

<main class="flex w-full flex-col items-center gap-5 px-3 py-7 sm:px-10 md:gap-10 xl:px-15">
	<GoBack title="Criar Evento" description="Crie o próximo evento" />
	<!-- <BoardGameForm bind:isValid={formIsValid} {formValues} /> -->
	<Button
		text="Criar Evento"
		width="290px"
		height="40px"
		onClick={handleOnCreate}
		loading={isLoading}
	/>
</main>
