<script lang="ts">
	import { EUserRole } from '$lib/shared/enums/user-role.enum';
	import { faBarcode } from '@fortawesome/free-solid-svg-icons';
	import IconButton from '../atoms/IconButton.svelte';
	import SearchInput from '../atoms/SearchInput.svelte';
	import BoardGame from '../molecules/BoardGame.svelte';
	import type { IBoardGameForList } from '$lib/shared/interfaces/board-games';

	export let gamesList: IBoardGameForList[];
	export let onClickGame: (gameId: string) => void;
	export let onClickLoanGame: ((gameId: string) => void) | null = null;

	let searchInputValue: string = '';

	function handleOnScanBarCode(): void {}

	$: filteredGamesList = gamesList.filter((game) => {
		const searchValue = searchInputValue.toLowerCase();
		return (
			game.name.toLowerCase().includes(searchValue) || game.barcode.toString().includes(searchValue)
		);
	});
</script>

<div class="flex w-full max-w-150 flex-col items-center justify-center gap-8">
	<h3 class="h3 w-full text-start">Jogos</h3>
	<div class="flex flex-col gap-8">
		<div class="flex w-full items-center justify-start gap-3">
			<IconButton
				icon={faBarcode}
				onClick={handleOnScanBarCode}
				width="60px"
				height="40px"
				fontSize="26px"
			/>
			<SearchInput bind:value={searchInputValue} placeholder="Pesquisar..." />
		</div>
		<div class="flex max-h-76 w-fit flex-col gap-2 overflow-y-auto px-1 py-2 md:px-4">
			{#if filteredGamesList.length > 0}
				{#each filteredGamesList as game}
					<BoardGame
						title={game.name}
						category={game.category}
						minParticipants={game.minParticipants}
						maxParticipants={game.maxParticipants}
						isActivate={game.isAvailable == undefined ? true : game.isAvailable}
						userType={!!onClickLoanGame ? EUserRole.ADMIN : EUserRole.USER}
						onClick={() => onClickGame(game.id)}
						onAdminClick={!!onClickLoanGame ? () => onClickLoanGame(game.id) : undefined}
					/>
				{/each}
			{:else}
				<p>Nenhum jogo encontrado.</p>
			{/if}
		</div>
	</div>
</div>
