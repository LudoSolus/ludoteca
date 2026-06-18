import { toast } from 'svoast';
import type { TReportType, TReportFormat } from '$lib/types/report.type';

export class ReportService {
	private static instance: ReportService;

	private constructor() {}

	public static getInstance(): ReportService {
		if (!ReportService.instance) {
			ReportService.instance = new ReportService();
		}
		return ReportService.instance;
	}

	private convertToCSV<T extends object>(data: T[]): string {
		if (data.length === 0) return '';
		const headers = Object.keys(data[0]) as (keyof T)[];
		const csvRows = [];

		// BOM for Excel compatibility with UTF-8
		csvRows.push('\ufeff' + headers.join(','));

		for (const row of data) {
			const values = headers.map((header) => {
				const val = row[header];
				const escaped = ('' + (val ?? '')).replace(/"/g, '""');
				return `"${escaped}"`;
			});
			csvRows.push(values.join(','));
		}

		return csvRows.join('\n');
	}

	private triggerDownload(content: string, filename: string, mimeType: string) {
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

	private generatePDFReport(title: string, headers: string[], rows: any[][]) {
		const printWindow = window.open('', '_blank');
		if (!printWindow) {
			toast.error(
				'Não foi possível abrir a janela de visualização do PDF. Verifique se o bloqueador de pop-ups está ativado.',
				{ closable: true }
			);
			return;
		}

		const dateStr = new Date().toLocaleString('pt-BR');

		const html = `
			<!DOCTYPE html>
			<html>
			<head>
				<meta charset="utf-8">
				<title>${title}</title>
				<style>
					@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=Inknut+Antiqua:wght@600&display=swap');
					body {
						font-family: 'Inter', sans-serif;
						margin: 40px 30px;
						color: #111827;
						background-color: #fff;
						line-height: 1.5;
					}
					.header {
						display: flex;
						justify-content: space-between;
						align-items: center;
						border-bottom: 3px solid #fbd322;
						padding-bottom: 15px;
						margin-bottom: 30px;
					}
					.logo-area h1 {
						font-family: 'Inknut Antiqua', serif;
						font-size: 24px;
						margin: 0;
						color: #000;
					}
					.logo-area p {
						margin: 5px 0 0 0;
						font-size: 12px;
						color: #4b5563;
					}
					.meta-area {
						text-align: right;
						font-size: 11px;
						color: #4b5563;
					}
					.meta-area p {
						margin: 2px 0;
					}
					.report-title {
						font-family: 'Inknut Antiqua', serif;
						font-size: 18px;
						margin-top: 0;
						margin-bottom: 25px;
						color: #000;
						text-transform: uppercase;
						letter-spacing: 0.5px;
					}
					table {
						width: 100%;
						border-collapse: collapse;
						margin-bottom: 35px;
						font-size: 12px;
					}
					th {
						background-color: #fbd322;
						color: #000;
						font-weight: 700;
						text-align: left;
						padding: 10px 12px;
						border: 1px solid #000;
						font-size: 11px;
						text-transform: uppercase;
					}
					td {
						padding: 10px 12px;
						border: 1px solid #e5e7eb;
						word-break: break-word;
					}
					tr:nth-child(even) {
						background-color: #fffdf2;
					}
					.footer {
						position: fixed;
						bottom: 20px;
						left: 30px;
						right: 30px;
						display: flex;
						justify-content: space-between;
						font-size: 10px;
						color: #9ca3af;
						border-top: 1px solid #e5e7eb;
						padding-top: 10px;
					}
					@media print {
						body {
							margin: 20px 15px;
						}
						.footer {
							position: fixed;
							bottom: 10px;
						}
						table {
							page-break-inside: auto;
						}
						tr {
							page-break-inside: avoid;
							page-break-after: auto;
						}
						thead {
							display: table-header-group;
						}
					}
				</style>
			</head>
			<body>
				<div class="header">
					<div class="logo-area">
						<h1>Ludoteca</h1>
						<p>Sistema de Gestão de Jogos e Eventos</p>
					</div>
					<div class="meta-area">
						<p><strong>Gerado em:</strong> ${dateStr}</p>
						<p><strong>Formato:</strong> PDF (Impressão)</p>
					</div>
				</div>
				
				<div class="report-title">${title}</div>
				
				<table>
					<thead>
						<tr>
							${headers.map((h) => `<th>${h}</th>`).join('')}
						</tr>
					</thead>
					<tbody>
						${rows
							.map(
								(row) => `
							<tr>
								${row.map((val) => `<td>${val ?? ''}</td>`).join('')}
							</tr>
						`
							)
							.join('')}
					</tbody>
				</table>
				
				<div class="footer">
					<span>Ludoteca &copy; ${new Date().getFullYear()}</span>
					<span>Relatório Administrativo Oficial</span>
				</div>
				
				<script>
					window.onload = function() {
						window.print();
						setTimeout(function() {
							window.close();
						}, 1000);
					};
				</script>
			</body>
			</html>
		`;

		printWindow.document.open();
		printWindow.document.write(html);
		printWindow.document.close();
	}

	public exportReport(
		reportType: TReportType,
		format: TReportFormat,
		rawData: any[]
	): void {
		if (!rawData || rawData.length === 0) {
			toast.info('Nenhum dado disponível para exportar.', { closable: true });
			return;
		}

		let mappedData: any[] = [];
		let filePrefix = '';
		let reportTitle = '';

		switch (reportType) {
			case 'users':
				mappedData = rawData.map((user) => ({
					ID: user.id,
					'E-mail': user.email,
					'Faixa Etária': user.ageCategory === '+18' ? 'Maioridade' : user.ageCategory,
					Perfil: user.roleUser === 'ADMIN' ? 'Administrador' : 'Usuário'
				}));
				filePrefix = 'usuarios_cadastrados';
				reportTitle = 'Relatório de Usuários Cadastrados';
				break;

			case 'games':
				mappedData = rawData.map((game) => ({
					ID: game.id,
					'Código de Barras': game.barcode,
					'Nome do Jogo': game.name,
					Categoria: game.category,
					'Mínimo Jogadores': game.minParticipants,
					'Máximo Jogadores': game.maxParticipants
				}));
				filePrefix = 'jogos_acervo';
				reportTitle = 'Relatório de Jogos do Acervo';
				break;

			case 'events':
				mappedData = rawData.map((event) => ({
					ID: event.id,
					'Nome do Evento': event.name,
					Status:
						event.status === 'SCHEDULED'
							? 'Agendado'
							: event.status === 'INPROGRESS'
								? 'Em Andamento'
								: 'Finalizado',
					'Data de Início': new Date(event.startDate).toLocaleString('pt-BR'),
					'Data de Término': new Date(event.finalDate).toLocaleString('pt-BR'),
					Endereço: `${event.street}, ${event.number} - ${event.neighborhood}, ${event.city}/${event.state} (CEP: ${event.zipCode})`,
					'Jogos de Tabuleiro': event.hasBoardGames ? 'Sim' : 'Não',
					RPG: event.hasRpg ? 'Sim' : 'Não',
					'Escape Room': event.hasEscapeRoom ? 'Sim' : 'Não'
				}));
				filePrefix = 'eventos_ludoteca';
				reportTitle = 'Relatório de Eventos da Ludoteca';
				break;

			case 'most_played':
				mappedData = rawData.map((item) => ({
					'Título do Jogo': item.gameTitle || item.gameName || '',
					'Total de Empréstimos': item.loanCount || 0
				}));
				filePrefix = 'jogos_mais_jogados';
				reportTitle = 'Relatório de Jogos Mais Jogados';
				break;

			case 'event_participants':
				mappedData = rawData.map((item) => ({
					'Nome do Evento': item.eventName || '',
					'Total de Participantes': item.participantCount || 0
				}));
				filePrefix = 'participacao_por_evento';
				reportTitle = 'Relatório de Participação por Evento';
				break;
		}

		if (format === 'pdf') {
			if (mappedData.length === 0) {
				toast.info('Nenhum dado disponível para gerar o PDF.', { closable: true });
				return;
			}
			const headers = Object.keys(mappedData[0]);
			const rows = mappedData.map((obj) => headers.map((key) => obj[key]));
			this.generatePDFReport(reportTitle, headers, rows);
			toast.success('Relatório PDF enviado para impressão!', { closable: true });
		} else {
			let content = '';
			let filename = '';
			let mimeType = '';

			if (format === 'csv') {
				content = this.convertToCSV(mappedData);
				filename = `${filePrefix}_${new Date().toISOString().slice(0, 10)}.csv`;
				mimeType = 'text/csv;charset=utf-8;';
			} else {
				content = JSON.stringify(mappedData, null, 2);
				filename = `${filePrefix}_${new Date().toISOString().slice(0, 10)}.json`;
				mimeType = 'application/json;charset=utf-8;';
			}

			this.triggerDownload(content, filename, mimeType);
			toast.success('Relatório exportado com sucesso!', { closable: true });
		}
	}
}

export const reportService = ReportService.getInstance();
