<script lang="ts">
	import { onMount } from 'svelte';
	import { page } from '$app/stores';
	import axios from 'axios';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import { ListUsersQuery } from '$lib/api/queries/users/list-users/list-users.query';
	import { ListBoardGamesQuery } from '$lib/api/queries/board-games/list-board-games/list-board-games.query';
	import { ListAllEventsQuery } from '$lib/api/queries/events/list-all-events/list-all-events.query';
	import { MostPlayedGamesQuery } from '$lib/api/queries/dashboard/most-played-games/most-played-games.query';
	import { ParticipantsByEventQuery } from '$lib/api/queries/dashboard/participants-by-event/participants-by-event.query';
	import { reportService } from '$lib/services/report.service';
	import type { TReportType, TReportFormat } from '$lib/types/report.type';
	import ReportViewerTemplate from '$lib/components/templates/admin/ReportViewerTemplate.svelte';

	const queriesHandler = new QueriesHandlerService(axios);

	let reportData: any[] | null = null;
	let exporting = false;

	$: currentType = $page.params.type as TReportType;

	// Recarrega os dados caso o tipo na URL mude
	$: if (currentType) {
		loadReportData();
	}

	function loadReportData() {
		reportData = null; // Coloca em loading state

		switch (currentType) {
			case 'users':
				queriesHandler.handle(new ListUsersQuery()).subscribe({
					next: (res) => {
						reportData = res.resultData || [];
					},
					error: (err) => {
						console.error('Erro ao buscar usuários do relatório', err);
						reportData = [];
					}
				});
				break;

			case 'games':
				queriesHandler.handle(new ListBoardGamesQuery()).subscribe({
					next: (res) => {
						reportData = res.resultData || [];
					},
					error: (err) => {
						console.error('Erro ao buscar jogos do relatório', err);
						reportData = [];
					}
				});
				break;

			case 'events':
				queriesHandler.handle(new ListAllEventsQuery()).subscribe({
					next: (res) => {
						reportData = res.resultData || [];
					},
					error: (err) => {
						console.error('Erro ao buscar eventos do relatório', err);
						reportData = [];
					}
				});
				break;

			case 'most_played':
				queriesHandler.handle(new MostPlayedGamesQuery()).subscribe({
					next: (res) => {
						reportData = res.resultData?.data || [];
					},
					error: (err) => {
						console.error('Erro ao buscar jogos mais jogados do relatório', err);
						reportData = [];
					}
				});
				break;

			case 'event_participants':
				queriesHandler.handle(new ParticipantsByEventQuery()).subscribe({
					next: (res) => {
						reportData = res.resultData?.data || [];
					},
					error: (err) => {
						console.error('Erro ao buscar participantes dos eventos', err);
						reportData = [];
					}
				});
				break;

			default:
				reportData = [];
				break;
		}
	}

	function handleExport(format: TReportFormat) {
		if (!reportData || reportData.length === 0) return;
		exporting = true;
		reportService.exportReport(currentType, format, reportData);
		exporting = false;
	}
</script>

<ReportViewerTemplate
	reportType={currentType}
	data={reportData}
	{exporting}
	onExport={handleExport}
/>
