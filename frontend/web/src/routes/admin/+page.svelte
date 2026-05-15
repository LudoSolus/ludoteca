<script lang="ts">
	import { onMount } from 'svelte';
	import axios from 'axios';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import { RegisteredUsersCountQuery } from '$lib/api/queries/dashboard/registered-users-count/registered-users-count.query';
	import { MostPlayedGamesQuery } from '$lib/api/queries/dashboard/most-played-games/most-played-games.query';
	import { ParticipantsByEventQuery } from '$lib/api/queries/dashboard/participants-by-event/participants-by-event.query';
	import DashboardManager from '$lib/components/templates/admin/DashboardManager.svelte';
	import type { IMostPlayedGamesData } from '$lib/api/queries/dashboard/most-played-games/most-played-games.interface';
	import type { IParticipantsByEventData } from '$lib/api/queries/dashboard/participants-by-event/participants-by-event.interface';

	const queriesHandler = new QueriesHandlerService(axios);
	
	let totalUsers: number = 0;
	let mostPlayedGamesData: IMostPlayedGamesData[] = [];
	let participantsByEventData: IParticipantsByEventData[] = [];

	onMount(() => {
		fetchDashboardData();
	});

	function fetchDashboardData() {
		// Fetch Total Users
		queriesHandler.handle(new RegisteredUsersCountQuery()).subscribe({
			next: (res) => {
				totalUsers = res.resultData?.totalUsers || 0;
			},
			error: (err) => {
				console.error('Erro ao buscar total de usuários', err);
			}
		});

		// Fetch Most Played Games
		queriesHandler.handle(new MostPlayedGamesQuery()).subscribe({
			next: (res) => {
				mostPlayedGamesData = res.resultData?.data || [];
			},
			error: (err) => {
				console.error('Erro ao buscar jogos mais jogados', err);
			}
		});

		// Fetch Participants By Event
		queriesHandler.handle(new ParticipantsByEventQuery()).subscribe({
			next: (res) => {
				participantsByEventData = res.resultData?.data || [];
			},
			error: (err) => {
				console.error('Erro ao buscar participantes por evento', err);
			}
		});
	}
</script>

<DashboardManager 
	{totalUsers} 
	{mostPlayedGamesData} 
	{participantsByEventData} 
/>
