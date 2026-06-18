<script lang="ts">
	import { onMount } from 'svelte';
	import { page } from '$app/stores';
	import axios from 'axios';
	import { toast } from 'svoast';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import { ListUsersQuery } from '$lib/api/queries/users/list-users/list-users.query';
	import { ListBoardGamesQuery } from '$lib/api/queries/board-games/list-board-games/list-board-games.query';
	import { ListAllEventsQuery } from '$lib/api/queries/events/list-all-events/list-all-events.query';
	import { MostPlayedGamesQuery } from '$lib/api/queries/dashboard/most-played-games/most-played-games.query';
	import { ParticipantsByEventQuery } from '$lib/api/queries/dashboard/participants-by-event/participants-by-event.query';
	import { DefaultersQuery } from '$lib/api/queries/reports/defaulters/defaulters.query';
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

			case 'defaulters':
				queriesHandler.handle(new DefaultersQuery()).subscribe({
					next: (res) => {
						reportData = res.resultData || [];
					},
					error: (err) => {
						console.error('Erro ao buscar inadimplentes do relatório', err);
						reportData = [];
					}
				});
				break;

			default:
				reportData = [];
				break;
		}
	}

	function getFilePrefix(): string {
		switch (currentType) {
			case 'users':
				return 'usuarios_cadastrados';
			case 'games':
				return 'jogos_acervo';
			case 'events':
				return 'eventos_ludoteca';
			case 'most_played':
				return 'jogos_mais_jogados';
			case 'event_participants':
				return 'participacao_por_evento';
			case 'defaulters':
				return 'usuarios_inadimplentes';
			default:
				return 'relatorio';
		}
	}

	function getReportTitle(): string {
		switch (currentType) {
			case 'users':
				return 'Relatório de Usuários Cadastrados';
			case 'games':
				return 'Relatório de Jogos do Acervo';
			case 'events':
				return 'Relatório de Eventos da Ludoteca';
			case 'most_played':
				return 'Relatório de Jogos Mais Jogados';
			case 'event_participants':
				return 'Relatório de Participação por Evento';
			case 'defaulters':
				return 'Relatório de Atrasos e Devoluções';
			default:
				return 'Relatório Administrativo';
		}
	}

	function triggerDownload(content: string, filename: string, mimeType: string) {
		const blob = new Blob([content], { type: mimeType });
		const url = URL.createObjectURL(blob);
		const link = document.createElement('a');
		link.href = url;
		link.setAttribute('download', filename);
		document.body.appendChild(link);
		link.click();
		document.body.removeChild(link);
		URL.revokeObjectURL(url);
	}

	async function handleExport(format: TReportFormat) {
		if (!reportData || reportData.length === 0) {
			toast.info('Nenhum dado disponível para exportar.', { closable: true });
			return;
		}

		exporting = true;
		try {
			const mappedData = reportService.formatDataForReport(currentType, reportData);

			if (format === 'pdf') {
				// Importações dinâmicas para compatibilidade segura com SSR no SvelteKit
				const { jsPDF } = await import('jspdf');
				const { default: autoTable } = await import('jspdf-autotable');

				const doc = new jsPDF();
				const tableElement = document.getElementById('report-table');
				
				if (tableElement) {
					const title = getReportTitle();
					const dateStr = new Date().toLocaleString('pt-BR');

					// Cabeçalho institucional no PDF
					doc.setFont('helvetica', 'bold');
					doc.setFontSize(14);
					doc.text(title, 14, 15);
					
					doc.setFont('helvetica', 'normal');
					doc.setFontSize(9);
					doc.setTextColor(100, 100, 100);
					doc.text(`Gerado em: ${dateStr} | Ludoteca - Gestão de Jogos e Eventos`, 14, 21);

					// Converte o elemento HTML table do DOM em PDF de forma local
					autoTable(doc, {
						html: '#report-table',
						theme: 'grid',
						headStyles: {
							fillColor: [251, 211, 34], // bg-yellow-400 (#fbd322)
							textColor: [0, 0, 0],
							fontStyle: 'bold',
							lineWidth: 0.1,
							lineColor: [0, 0, 0]
						},
						styles: {
							font: 'helvetica',
							fontSize: 9
						},
						margin: { top: 26 }
					});

					const filename = `${getFilePrefix()}_${new Date().toISOString().slice(0, 10)}.pdf`;
					doc.save(filename);
					toast.success('Relatório PDF exportado com sucesso!', { closable: true });
				} else {
					toast.error('Elemento da tabela não encontrado.', { closable: true });
				}
			} else {
				let content = '';
				let filename = '';
				let mimeType = '';

				if (format === 'csv') {
					content = reportService.convertToCSV(mappedData);
					filename = `${getFilePrefix()}_${new Date().toISOString().slice(0, 10)}.csv`;
					mimeType = 'text/csv;charset=utf-8;';
				} else {
					content = reportService.convertToJSON(mappedData);
					filename = `${getFilePrefix()}_${new Date().toISOString().slice(0, 10)}.json`;
					mimeType = 'application/json;charset=utf-8;';
				}

				triggerDownload(content, filename, mimeType);
				toast.success('Relatório exportado com sucesso!', { closable: true });
			}
		} catch (error) {
			console.error('Erro na exportação:', error);
			toast.error('Erro ao exportar o relatório.', { closable: true });
		} finally {
			exporting = false;
		}
	}
</script>

<ReportViewerTemplate
	reportType={currentType}
	data={reportData}
	{exporting}
	onExport={handleExport}
/>
