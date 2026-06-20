<script lang="ts">
	import type { IEditBoardGameRequest } from '$lib/api/commands/board-games/edit-board-game/edit-board-game.interface';
	import type { IGetBoardGameDetailsResponse } from '$lib/api/queries/board-games/get-board-game-details/get-board-game-details.interface';
	import Button from '$lib/components/atoms/Button.svelte';
	import BoardGameForm from '$lib/components/molecules/forms/BoardGameForm.svelte';
	import GoBack from '$lib/components/molecules/GoBack.svelte';
	import { ECategory } from '$lib/shared/enums/category.enum';
	import { getEnumKeyByValue } from '$lib/shared/helpers/get-enum-key-by-value';
	import { toast } from 'svoast';

	let {
		boardGame = null,
		isLoading,
		onEditBoardGame
	} = $props<{
		boardGame: IGetBoardGameDetailsResponse | null;
		isLoading: boolean;
		onEditBoardGame: (data: IEditBoardGameRequest) => void;
	}>();

	let formIsValid = $state(false);
	let formValues = $state<Record<keyof IEditBoardGameRequest, string>>({
		barcode: '',
		title: '',
		category: '',
		description: '',
		minPlayers: '',
		maxPlayers: '',
		linkInstructionManual: '',
		linkVideoTutorial: ''
	});

	$effect(() => {
		if (boardGame) {
			formValues = {
				barcode: String(boardGame.barcode),
				title: boardGame.title,
				category: getEnumKeyByValue(ECategory, boardGame.category) ?? '',
				description: boardGame.description,
				minPlayers: String(boardGame.minPlayers),
				maxPlayers: String(boardGame.maxPlayers),
				linkInstructionManual: boardGame.linkInstructionManual,
				linkVideoTutorial: boardGame.linkVideoTutorial
			};
		}
	});

	function handleOnEdit() {
		if (!formIsValid) {
			toast.error('Preencha todos os campos devidamente.', { closable: true });
			return;
		}

		const formValuesFormated: IEditBoardGameRequest = {
			...formValues,
			barcode: Number(formValues.barcode.replace(/\D+/g, '')),
			minPlayers: Number(formValues.minPlayers.replace(/\D+/g, '')),
			maxPlayers: Number(formValues.maxPlayers.replace(/\D+/g, ''))
		};

		onEditBoardGame(formValuesFormated);
	}
</script>

<main
	class="flex w-full flex-col items-center gap-5 px-3 py-7 sm:px-10 md:gap-10 xl:px-15"
>
	<GoBack title="Editar Jogo" description="Edite os dados do jogo" />
	{#if boardGame === null}
		<div
			class="grid w-full max-w-230 grid-cols-1 place-items-center gap-0 sm:grid-cols-2 sm:gap-3 xl:gap-5"
		>
			<div class="flex w-full flex-col gap-2" style="height: 90px;">
				<div class="skeleton-pulse h-4 w-28 rounded bg-gray-300"></div>
				<div class="skeleton-pulse h-10 w-full rounded border border-gray-200 bg-gray-100"></div>
			</div>
			<div class="flex w-full flex-col gap-2" style="height: 90px;">
				<div class="skeleton-pulse h-4 w-28 rounded bg-gray-300"></div>
				<div class="skeleton-pulse h-10 w-full rounded border border-gray-200 bg-gray-100"></div>
			</div>

			<div class="flex w-full flex-col gap-2 sm:col-span-2">
				<div class="skeleton-pulse h-4 w-28 rounded bg-gray-300"></div>
				<div class="skeleton-pulse h-24 w-full rounded border border-gray-200 bg-gray-100"></div>
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
			<div class="flex w-full flex-col gap-2" style="height: 90px;">
				<div class="skeleton-pulse h-4 w-28 rounded bg-gray-300"></div>
				<div class="skeleton-pulse h-10 w-full rounded border border-gray-200 bg-gray-100"></div>
			</div>
		</div>
		<div class="skeleton-pulse mt-5 h-10 w-[290px] rounded bg-gray-300"></div>
	{:else}
		<BoardGameForm bind:isValid={formIsValid} bind:formValues={formValues} />
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
