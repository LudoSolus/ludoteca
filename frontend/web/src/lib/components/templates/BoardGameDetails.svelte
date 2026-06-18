<script lang="ts">
	import type { IGetBoardGameDetailsWithLoanResponse } from '$lib/api/queries/board-games/get-board-game-details-with-loan/get-board-game-details-with-loan.interface';
	import Fa from 'svelte-fa';
	import { faHistory } from '@fortawesome/free-solid-svg-icons';
	import LoanHistoryContainer from '$lib/components/atoms/LoanHistoryContainer.svelte';
	import GoBack from '$lib/components/molecules/GoBack.svelte';

	export let boardGameDetails: IGetBoardGameDetailsWithLoanResponse | null = null;
	export let handleOnEdit: (() => void) | null = null;
	export let handleOnDelete: (() => void) | null = null;
	export let isLoading: boolean;
	export let type: 'admin' | 'user';
</script>

<main class="flex h-full flex-col items-center gap-10 px-1 py-7 sm:px-10 xl:px-15">
	{#if boardGameDetails === null}
		<GoBack
			title="Carregando..."
			description="Detalhes do jogo"
			onEdit={null}
			onDelete={null}
			isLoadingDelete={false}
		/>
		<div
			class="flex w-full max-w-350 flex-wrap items-start justify-between gap-10 px-0 pb-20 sm:px-2 lg:px-4 xl:px-10"
		>
			<section class="flex w-full flex-1 flex-col items-start justify-start gap-4">
				<div class="flex flex-col gap-2 w-full lg:min-h-50">
					<div class="skeleton-pulse h-4 w-full rounded bg-gray-300"></div>
					<div class="skeleton-pulse h-4 w-5/6 rounded bg-gray-300"></div>
					<div class="skeleton-pulse h-4 w-3/4 rounded bg-gray-300"></div>
				</div>
				<div class="flex w-full flex-col items-start justify-start gap-3">
					<div class="flex w-full gap-2 items-center">
						<div class="skeleton-pulse h-4 w-20 rounded bg-gray-300"></div>
						<div class="skeleton-pulse h-4 w-32 rounded bg-gray-300"></div>
					</div>
					<div class="flex w-full gap-2 items-center">
						<div class="skeleton-pulse h-4 w-28 rounded bg-gray-300"></div>
						<div class="skeleton-pulse h-4 w-40 rounded bg-gray-300"></div>
					</div>
					<div class="flex w-full gap-2 items-center">
						<div class="skeleton-pulse h-4 w-16 rounded bg-gray-300"></div>
						<div class="skeleton-pulse h-4 w-64 rounded bg-gray-300"></div>
					</div>
					<div class="flex w-full gap-2 items-center">
						<div class="skeleton-pulse h-4 w-16 rounded bg-gray-300"></div>
						<div class="skeleton-pulse h-4 w-64 rounded bg-gray-300"></div>
					</div>
				</div>
			</section>
			{#if type === 'admin'}
				<section class="flex flex-1 flex-col items-center justify-center gap-8">
					<h3 class="h3 w-full text-start">Empréstimos</h3>
					<div
						class="flex max-h-90 w-full flex-col items-center gap-4 overflow-y-auto px-1 py-2 md:px-4"
					>
						{#each Array(3) as _}
							<div class="skeleton-pulse h-14 w-full rounded bg-gray-200"></div>
						{/each}
					</div>
				</section>
			{/if}
		</div>
	{:else}
		<GoBack
			title={boardGameDetails.title}
			description="Detalhes do jogo"
			onEdit={type === 'admin' ? handleOnEdit : null}
			onDelete={type === 'admin' ? handleOnDelete : null}
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
			{#if type === 'admin'}
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
							<div
								class="flex w-full flex-col items-center justify-center gap-3 rounded-2xl border-2 border-dashed border-black p-5 text-center shadow-[0px_4px_10px_rgba(0,0,0,0.25)]"
							>
								<div
									class="flex h-12 w-12 items-center justify-center rounded-full border-2 border-black bg-[var(--primary-color)] text-black"
								>
									<Fa icon={faHistory} size="lg" />
								</div>
								<div class="flex flex-col gap-1">
									<h4 class="inknut text-sm font-bold">Nenhum empréstimo</h4>
									<p class="text-xs text-gray-700">Este jogo ainda não foi emprestado.</p>
								</div>
							</div>
						{/if}
					</div>
				</section>
			{/if}
		</div>
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
