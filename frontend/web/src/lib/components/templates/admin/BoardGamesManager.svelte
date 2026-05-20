<script lang="ts">
	import type { IBoardGame } from '$lib/api/queries/board-games/list-board-games/list-board-games.interface';
	import defaultImg from '$lib/assets/default.png';
	import { goto } from '$app/navigation';
	import BoardGamesList from '$lib/components/organisms/BoardGamesList.svelte';
	import Button from '$lib/components/atoms/Button.svelte';
	import { faPlus } from '@fortawesome/free-solid-svg-icons';

	export let boardGames: IBoardGame[];

	function goToBoardGameDetails(boardGameId: string) {
		goto(`/admin/board-games/${boardGameId}`);
	}
</script>

<main class="flex w-full flex-col items-center justify-start px-4 py-12 sm:px-10 xl:px-15 gap-5">
	<div class="flex w-full justify-end">
		<Button text="Criar" leftIcon={faPlus} onClick={() => goto('/admin/board-games/create')} />
	</div>
	<div class="flex w-full max-w-350 h-full items-center justify-center gap-20 lg:justify-between">
		<section class="flex max-w-150 flex-1 flex-col items-center justify-center gap-8">
			<BoardGamesList gamesList={boardGames} onClickGame={goToBoardGameDetails} />
		</section>
		<section class="hidden h-full max-w-100 flex-1 items-center justify-center lg:flex">
			<img src={defaultImg} alt="Imagem padrão" />
		</section>
	</div>
</main>
