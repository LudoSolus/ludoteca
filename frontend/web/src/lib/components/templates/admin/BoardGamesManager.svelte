<script lang="ts">
	import type { IBoardGame } from '$lib/api/queries/board-games/list-board-games/list-board-games.interface';
	import BoardGame from '$lib/components/molecules/BoardGame.svelte';
	import SearchInput from '$lib/components/atoms/SearchInput.svelte';
	import defaultImg from '$lib/assets/default.png';
	import Button from '$lib/components/atoms/Button.svelte';
	import { faPlus } from '@fortawesome/free-solid-svg-icons';
	import { goto } from '$app/navigation';

	export let boardGames: IBoardGame[];

	let searchInputValue: string = '';

	function goToBoardGameDetails(boardGameId: string) {
		goto(`/admin/board-games/${boardGameId}`);
	}

	$: filteredGamesList = boardGames.filter((game) => {
		return game.name.toLowerCase().includes(searchInputValue.toLowerCase());
	});
</script>

<main
	class="flex h-full items-start justify-center gap-20 overflow-y-auto px-4 py-7 sm:px-10 lg:justify-between xl:px-15"
>
	<section class="flex max-w-200 flex-1 flex-col items-center justify-center gap-8 pt-0 lg:pt-20">
		<div class="flex w-full items-center justify-between">
			<h3 class="h3">Jogos</h3>
			<Button text="Criar" leftIcon={faPlus} onClick={() => {}} />
		</div>

		<div class="flex w-full max-w-150 flex-col gap-4 sm:gap-8">
			
			<div class="flex w-full max-w-150 items-center justify-start gap-5 px-2 sm:px-4">
				<SearchInput bind:value={searchInputValue} placeholder="Pesquisar..." />
			</div>
			<div
				class="flex max-h-76 w-full flex-col items-center gap-2 overflow-y-auto px-1 py-2 md:px-4"
			>
				{#if filteredGamesList.length > 0}
					{#each filteredGamesList as game}
						<BoardGame
							title={game.name}
							category={game.category}
							minParticipants={game.minParticipants}
							maxParticipants={game.maxParticipants}
							isActivate={true}
							onClick={() => {
								goToBoardGameDetails(game.id);
							}}
						/>
					{/each}
				{:else}
					<p class="w-full">Nenhum jogo encontrado.</p>
				{/if}
			</div>
		</div>
	</section>
	<section class="hidden h-full max-w-100 flex-1 items-center justify-center lg:flex">
		<img src={defaultImg} alt="Imagem padrão" />
	</section>
</main>
