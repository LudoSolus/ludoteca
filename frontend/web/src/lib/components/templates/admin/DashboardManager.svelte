<script lang="ts">
	import TotalUsersCard from '$lib/components/molecules/TotalUsersCard.svelte';
	import MostPlayedGamesChart from '$lib/components/molecules/chart/MostPlayedGamesChart.svelte';
	import ParticipantsByEventChart from '$lib/components/molecules/chart/ParticipantsByEventChart.svelte';
	import type { IMostPlayedGamesData } from '$lib/api/queries/dashboard/most-played-games/most-played-games.interface';
	import type { IParticipantsByEventData } from '$lib/api/queries/dashboard/participants-by-event/participants-by-event.interface';

	export let totalUsers: number;
	export let mostPlayedGamesData: IMostPlayedGamesData[];
	export let participantsByEventData: IParticipantsByEventData[];
</script>

<main class="flex w-full flex-col gap-8 px-4 py-8 sm:px-10 xl:px-15">
	<div class="flex w-full items-center justify-between">
		<h3>Dashboard</h3>
	</div>

	<div class="grid grid-cols-1 gap-6 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5">
		<div class="col-span-1">
			<TotalUsersCard {totalUsers} />
		</div>
	</div>

	<div class="mt-4 grid grid-cols-1 gap-6 xl:grid-cols-2">
		<MostPlayedGamesChart data={mostPlayedGamesData} />
		<ParticipantsByEventChart data={participantsByEventData} />
	</div>
</main>
