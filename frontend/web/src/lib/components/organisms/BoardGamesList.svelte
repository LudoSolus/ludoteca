<script lang="ts">
	import { EUserRole } from '$lib/shared/enums/user-role.enum';
	import Fa from 'svelte-fa';
	import { faDice } from '@fortawesome/free-solid-svg-icons';
	import SearchInput from '../atoms/SearchInput.svelte';
	import BoardGame from '../molecules/BoardGame.svelte';
	import BoardGameSkeleton from '../molecules/BoardGameSkeleton.svelte';
	import type { IBoardGameForList } from '$lib/shared/interfaces/board-games';

	export let gamesList: IBoardGameForList[] | undefined = undefined;
	export let onClickGame: (gameId: string) => void;
	export let onClickLoanGame: ((gameId: string) => void) | null = null;
	export let loading: boolean | undefined = undefined;

	let searchInputValue: string = '';

	function handleOnScanBarCode(): void {}

	$: isLoading = loading ?? gamesList === undefined;

	$: filteredGamesList = gamesList
		? gamesList.filter((game) => {
				const searchValue = searchInputValue.toLowerCase();
				return (
					game.name.toLowerCase().includes(searchValue) ||
					game.barcode.toString().includes(searchValue)
				);
			})
		: [];
</script>

<div class="flex h-full w-full max-w-150 flex-col items-center justify-start gap-8">
	<h3 class="h3 w-full text-start">Jogos</h3>
	<div class="box-list flex flex-col gap-8 px-1">
		<div class="flex w-full items-center justify-start">
			<SearchInput bind:value={searchInputValue} placeholder="Pesquisar..." />
		</div>
		<div class="board-games-list flex w-fit flex-col gap-2 overflow-y-auto px-1 md:px-4">
			{#if isLoading}
				{#each Array(4) as _}
					<BoardGameSkeleton userType={!!onClickLoanGame ? 'admin' : 'user'} />
				{/each}
			{:else if filteredGamesList.length > 0}
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
				<div
					class="flex w-[94vw] flex-col items-center justify-center gap-4 rounded-2xl border-2 border-dashed border-black bg-[var(--card-background-color)] p-6 text-center shadow-[0px_4px_10px_rgba(0,0,0,0.25)] min-[590px]:w-136"
				>
					<div
						class="flex h-14 w-14 items-center justify-center rounded-full border-2 border-black bg-[var(--primary-color)] text-black"
					>
						<Fa icon={faDice} size="lg" />
					</div>
					<div class="flex flex-col gap-1">
						<h4 class="inknut text-base font-bold">Nenhum jogo encontrado</h4>
						<p class="text-xs text-gray-700">Não encontramos nenhum jogo aqui.</p>
					</div>
				</div>
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
