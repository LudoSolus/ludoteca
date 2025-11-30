<script lang="ts">
	import { toast } from 'svoast';
	import FormInput from '../atoms/FormInput.svelte';
	import Modal from '../atoms/Modal.svelte';
	import type { IGetBoardGameByBarcodeResonse } from '$lib/api/queries/board-games/get-board-game-by-barcode/get-board-game-by-barcode.interface';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import axios from 'axios';
	import { GetBoardGameByBarcodeQuery } from '$lib/api/queries/board-games/get-board-game-by-barcode/get-board-game-by-barcode.query';

	interface IGameProp extends IGetBoardGameByBarcodeResonse {
		barcode: string;
	}

	const queriesHandler = new QueriesHandlerService(axios);

	export let isOpen: boolean;
	export let game: IGameProp | null = null;
	export let isLoading: boolean;
	export let onReturnGame: (gameId: string) => void;

	let gameBarcode: string = '';

	$: if (isOpen !== undefined) {
		if (game && game !== null) {
			gameBarcode = game.barcode;
		} else {
			gameBarcode = '';
		}
	}

	function searchBarCode() {
		queriesHandler.handle(new GetBoardGameByBarcodeQuery(gameBarcode)).subscribe({
			next: (res) => {
				game = {
					id: res.resultData.id,
					title: res.resultData.title,
					barcode: gameBarcode
				};
			},
			error: (err) => {
				toast.error('Jogo não encontrado.');
				game = null;
			}
		});
	}

	function handleOnReturnGame() {
		if (!game) {
			toast.error('Coloque um código de barras válido.');
			return;
		}

		onReturnGame(game.id);
	}

	function closeModal() {
		game = null;
		isOpen = false;
	}
</script>

{#if isOpen}
	<Modal
		onConfirm={handleOnReturnGame}
		onClose={closeModal}
		{isLoading}
		confirmButtonText="Devolver"
	>
		<div class="flex w-full flex-col gap-6">
			<p class="w-full text-center text-lg font-medium sm:text-xl">Devolver jogo</p>
			<div class="flex w-full max-w-130 flex-col items-start gap-4">
				<FormInput
					label={'Código de Barras'}
					placeholder="Escaneie o código de barras"
					bind:value={gameBarcode}
					onInput={(v) => {}}
					onChange={(e) => searchBarCode()}
					type="number"
				/>
				<p class="text-md">Jogo: {game?.title ?? '...'}</p>
			</div>
		</div>
	</Modal>
{/if}
