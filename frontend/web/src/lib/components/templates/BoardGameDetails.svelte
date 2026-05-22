<script lang="ts">
	import type { IGetBoardGameDetailsWithLoanResponse } from '$lib/api/queries/board-games/get-board-game-details-with-loan/get-board-game-details-with-loan.interface';
	import LoanHistoryContainer from '$lib/components/atoms/LoanHistoryContainer.svelte';
	import GoBack from '$lib/components/molecules/GoBack.svelte';

	export let boardGameDetails: IGetBoardGameDetailsWithLoanResponse;
	export let handleOnEdit: () => void;
	export let handleOnDelete: () => void;
	export let isLoading: boolean;
	export let type: 'admin' | 'user';
</script>

<main class="flex h-full flex-col items-center gap-10 px-1 py-7 sm:px-10 xl:px-15">
	<GoBack
		title={boardGameDetails.title}
		description="Detalhes do jogo"
		onEdit={handleOnEdit}
		onDelete={handleOnDelete}
		isLoadingDelete={isLoading}
	/>
	<div
		class="flex w-full max-w-350 flex-wrap items-start justify-between gap-10 px-0 pb-20 sm:px-2 lg:px-4 xl:px-10"
	>
		<section class="flex w-full flex-1 flex-col items-start justify-start gap-4">
			<p class="lg:min-h-50">{boardGameDetails.description}</p>
			<div class="flex w-full flex-col items-start justify-start gap-2">
				<div class="flex w-full gap-2">
					<p class="font-bold">Categoria:</p>
					<p>{boardGameDetails.category}</p>
				</div>
				<div class="flex w-full gap-2">
					<p class="font-bold">Participantes:</p>
					<p>{boardGameDetails.minPlayers} a {boardGameDetails.maxPlayers} participantes</p>
				</div>
				<div class="flex w-full flex-wrap gap-2">
					<p class="font-bold">Manual:</p>
					<a
						href={boardGameDetails.linkInstructionManual}
						class="anchor w-full truncate"
						target="_blank">{boardGameDetails.linkInstructionManual}</a
					>
				</div>
				<div class="flex w-full flex-wrap gap-2">
					<p class="font-bold">Tutorial:</p>
					<a
						href={boardGameDetails.linkVideoTutorial}
						class="anchor w-full truncate"
						target="_blank">{boardGameDetails.linkVideoTutorial}</a
					>
				</div>
			</div>
		</section>
		<section class="flex flex-1 flex-col items-center justify-center gap-8">
			<h3 class="h3 w-full text-start">Empréstimos</h3>
			<div
				class="flex max-h-90 w-full flex-col items-center gap-2 overflow-y-auto px-1 py-2 md:px-4"
			>
				{#if boardGameDetails.loanHistory.length > 0}
					{#each boardGameDetails.loanHistory as loan}
						<LoanHistoryContainer userEmail={loan.userEmail} loanDate={loan.loanDate} />
					{/each}
				{:else}
					<p class="w-full">Nenhum empréstimo realizado.</p>
				{/if}
			</div>
		</section>
	</div>
</main>
