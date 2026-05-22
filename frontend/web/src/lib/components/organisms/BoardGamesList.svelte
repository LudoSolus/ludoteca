<script lang="ts">
	import { EUserRole } from '$lib/shared/enums/user-role.enum';
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

<div class="flex h-full w-full max-w-150 flex-col items-center justify-start gap-8">
	<h3 class="h3 w-full text-start">Jogos</h3>
	<div class="box-list flex flex-col gap-8 px-1">
		<div class="flex w-full items-center justify-start">
			<SearchInput bind:value={searchInputValue} placeholder="Pesquisar..." />
		</div>
		<div class="board-games-list flex w-fit flex-col gap-2 overflow-y-auto px-1 md:px-4">
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
				<p class="w-[94vw] min-[590px]:w-136">Nenhum jogo encontrado.</p>
			{/if}
		</div>
	</div>
</div>

<style>
	.box-list {
		height: calc(100% - 77px);
	}

	.board-games-list {
		height: fit-content;
		max-height: calc(100% - 69px);
		scrollbar-width: thin;
	}
</style>
