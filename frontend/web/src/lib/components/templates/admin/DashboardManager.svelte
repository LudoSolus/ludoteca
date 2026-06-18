<script lang="ts">
	import TotalUsersCard from '$lib/components/molecules/TotalUsersCard.svelte';
	import MostPlayedGamesChart from '$lib/components/molecules/chart/MostPlayedGamesChart.svelte';
	import ParticipantsByEventChart from '$lib/components/molecules/chart/ParticipantsByEventChart.svelte';
	import type { IMostPlayedGamesData } from '$lib/api/queries/dashboard/most-played-games/most-played-games.interface';
	import type { IParticipantsByEventData } from '$lib/api/queries/dashboard/participants-by-event/participants-by-event.interface';
	import ReportLinkCard from '$lib/components/molecules/ReportLinkCard.svelte';
	import {
		faUsers,
		faDice,
		faCalendar,
		faChartLine,
		faUserGroup
	} from '@fortawesome/free-solid-svg-icons';

	export let totalUsers: number | null;
	export let mostPlayedGamesData: IMostPlayedGamesData[] | null;
	export let participantsByEventData: IParticipantsByEventData[] | null;

	const reportOptions = [
		{
			id: 'users' as const,
			title: 'Usuários',
			icon: faUsers
		},
		{
			id: 'games' as const,
			title: 'Acervo',
			icon: faDice
		},
		{
			id: 'events' as const,
			title: 'Eventos',
			icon: faCalendar
		},
		{
			id: 'most_played' as const,
			title: 'Mais Jogados',
			icon: faChartLine
		},
		{
			id: 'event_participants' as const,
			title: 'Presenças',
			icon: faUserGroup
		}
	];
</script>

<main class="flex w-full flex-col gap-8 px-4 py-8 sm:px-10 xl:px-15">
	<div class="flex w-full items-center justify-between">
		<h3>Dashboard</h3>
	</div>

	<div class="flex w-full flex-col gap-4">
		<h4 class="inknut text-base font-bold text-black">Relatórios Detalhados</h4>

		<div class="flex gap-3">
			{#each reportOptions as option}
				<ReportLinkCard href="/admin/reports/{option.id}" title={option.title} icon={option.icon} />
			{/each}
		</div>
	</div>

	<div class="grid grid-cols-1 gap-6 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5">
		<div class="col-span-1">
			{#if totalUsers === null}
				<div
					class="flex h-58 w-full max-w-70 flex-col items-center justify-center rounded-lg border border-gray-200 p-6"
				>
					<div class="skeleton-pulse mb-4 h-16 w-16 rounded-full bg-gray-200"></div>
					<div class="skeleton-pulse mb-2 h-6 w-5/6 rounded bg-gray-300"></div>
					<div class="skeleton-pulse h-10 w-16 rounded bg-gray-300"></div>
				</div>
			{:else}
				<TotalUsersCard {totalUsers} />
			{/if}
		</div>
	</div>

	<div class="mt-4 grid grid-cols-1 gap-6 xl:grid-cols-2">
		{#if mostPlayedGamesData === null}
			<div
				class="flex h-140 w-full flex-col justify-between rounded-lg border border-gray-200 bg-transparent p-6"
			>
				<div class="skeleton-pulse mx-auto h-6 w-48 rounded bg-gray-300"></div>
				<div class="flex h-96 items-end justify-between gap-4 px-4">
					<div class="skeleton-pulse w-full rounded bg-gray-200" style="height: 30%;"></div>
					<div class="skeleton-pulse w-full rounded bg-gray-200" style="height: 55%;"></div>
					<div class="skeleton-pulse w-full rounded bg-gray-200" style="height: 40%;"></div>
					<div class="skeleton-pulse w-full rounded bg-gray-200" style="height: 75%;"></div>
					<div class="skeleton-pulse w-full rounded bg-gray-200" style="height: 60%;"></div>
				</div>
				<div class="flex justify-between gap-4 px-4">
					<div class="skeleton-pulse h-4 w-16 rounded bg-gray-200"></div>
					<div class="skeleton-pulse h-4 w-16 rounded bg-gray-200"></div>
					<div class="skeleton-pulse h-4 w-16 rounded bg-gray-200"></div>
					<div class="skeleton-pulse h-4 w-16 rounded bg-gray-200"></div>
					<div class="skeleton-pulse h-4 w-16 rounded bg-gray-200"></div>
				</div>
			</div>
		{:else}
			<MostPlayedGamesChart data={mostPlayedGamesData} />
		{/if}

		{#if participantsByEventData === null}
			<div
				class="flex h-140 w-full flex-col justify-between rounded-lg border border-gray-200 bg-transparent p-6"
			>
				<div class="skeleton-pulse mx-auto h-6 w-64 rounded bg-gray-300"></div>
				<div class="flex h-96 items-end justify-between gap-4 px-4">
					<div class="skeleton-pulse w-full rounded bg-gray-200" style="height: 45%;"></div>
					<div class="skeleton-pulse w-full rounded bg-gray-200" style="height: 25%;"></div>
					<div class="skeleton-pulse w-full rounded bg-gray-200" style="height: 60%;"></div>
					<div class="skeleton-pulse w-full rounded bg-gray-200" style="height: 35%;"></div>
					<div class="skeleton-pulse w-full rounded bg-gray-200" style="height: 80%;"></div>
				</div>
				<div class="flex justify-between gap-4 px-4">
					<div class="skeleton-pulse h-4 w-16 rounded bg-gray-200"></div>
					<div class="skeleton-pulse h-4 w-16 rounded bg-gray-200"></div>
					<div class="skeleton-pulse h-4 w-16 rounded bg-gray-200"></div>
					<div class="skeleton-pulse h-4 w-16 rounded bg-gray-200"></div>
					<div class="skeleton-pulse h-4 w-16 rounded bg-gray-200"></div>
				</div>
			</div>
		{:else}
			<ParticipantsByEventChart data={participantsByEventData} />
		{/if}
	</div>
</main>

<style>
	.skeleton-pulse {
		animation: pulse 1.5s cubic-bezier(0.4, 0, 0.6, 1) infinite;
	}

	@keyframes pulse {
		0%,
		100% {
			opacity: 1;
		}
		50% {
			opacity: 0.4;
		}
	}
</style>
