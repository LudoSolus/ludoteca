<script lang="ts">
	import type { ICreateBoardGameRequest } from '$lib/api/commands/board-games/create-board-game/create-board-game.interface';
	import Button from '$lib/components/atoms/Button.svelte';
	import BoardGameForm from '$lib/components/molecules/forms/BoardGameForm.svelte';
	import GoBack from '$lib/components/molecules/GoBack.svelte';
	import { toast } from 'svoast';

	export let isLoading: boolean;
	export let onCreateBoardGame: (data: ICreateBoardGameRequest) => void;

	let formIsValid: boolean = false;
	let formValues: Record<keyof ICreateBoardGameRequest, string> = {
		barcode: '',
		title: '',
		category: '',
		description: '',
		minPlayers: '',
		maxPlayers: '',
		linkInstructionManual: '',
		linkVideoTutorial: ''
	};

	function handleOnCreate() {
		if (!formIsValid) {
			toast.error('Preencha todos os campos devidamente.', { closable: true });
			return;
		}

		const formValuesFormated: ICreateBoardGameRequest = {
			...formValues,
			barcode: Number(formValues.barcode.replace(/\D+/g, "")),
			minPlayers: Number(formValues.minPlayers.replace(/\D+/g, "")),
			maxPlayers: Number(formValues.maxPlayers.replace(/\D+/g, "")),
		}

		onCreateBoardGame(formValuesFormated)
	}
</script>

<main class="flex w-full flex-col items-center gap-5 md:gap-10 px-3 py-7 sm:px-10 xl:px-15">
	<GoBack title="Criar Jogo" description="Adicione um jogo a coleção" />
	<BoardGameForm bind:isValid={formIsValid} {formValues} />
	<Button text="Criar Jogo" width="290px" height="40px" onClick={handleOnCreate} loading={isLoading} />
</main>
