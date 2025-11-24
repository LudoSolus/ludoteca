<script lang="ts">
	import type { IBoardGame } from '$lib/api/queries/list-board-games/list-board-games.interface';
	import BoardGame from '../atoms/BoardGame.svelte';
	import SearchInput from '../atoms/SearchInput.svelte';
	import defaultImg from '$lib/assets/default.png';
	import Button from '../atoms/Button.svelte';
	import { faPlus } from '@fortawesome/free-solid-svg-icons';

	export let boardGames: IBoardGame[];

	let searchInputValue: string = '';

	$: filteredGamesList = boardGames.filter((game) => {
		return game.name.toLowerCase().includes(searchInputValue.toLowerCase());
	});
</script>

<main
	class="flex h-full items-start justify-center lg:justify-between gap-20 overflow-y-auto px-4 py-7 sm:px-10 xl:px-15"
>
	<section class="flex max-w-200 flex-1 flex-col items-center justify-center pt-0 lg:pt-20 gap-8">
		<div class="flex w-full items-center justify-between">
			<h3 class="h3">Jogos</h3>
			<Button text="Criar" leftIcon={faPlus} onClick={() => {}} />
		</div>

		<div class="flex flex-col gap-4 sm:gap-8 w-full max-w-150">
			<div class="flex w-full max-w-150 items-center justify-start gap-5 px-2 sm:px-4">
				<SearchInput bind:value={searchInputValue} placeholder="Pesquisar..." />
			</div>
			<div class="flex max-h-76 w-full items-center flex-col gap-2 overflow-y-auto px-1 py-2 md:px-4">
				{#if filteredGamesList.length > 0}
					{#each filteredGamesList as game}
						<BoardGame
							title={game.name}
							category={game.category}
							minParticipants={game.minParticipants}
							maxParticipants={game.maxParticipants}
							isActivate={true}
						/>
					{/each}
				{:else}
					<p class="w-full">Nenhum jogo encontrado.</p>
				{/if}
			</div>
		</div>
	</section>
	<section class="hidden lg:flex h-full max-w-100 flex-1 items-center justify-center">
		<img src={defaultImg} alt="Imagem padrão" />
	</section>
</main>
