import type { TReportType } from '$lib/types/report.type';

export class ReportService {
	private static instance: ReportService;

	private constructor() {}

	public static getInstance(): ReportService {
		if (!ReportService.instance) {
			ReportService.instance = new ReportService();
		}
		return ReportService.instance;
	}

	/**
	 * Mapeia os dados brutos recebidos da API para o formato padronizado de exibição e exportação.
	 */
	public formatDataForReport(reportType: TReportType, rawData: any[]): any[] {
		if (!rawData || rawData.length === 0) return [];

		switch (reportType) {
			case 'users':
				return rawData.map((user) => ({
					'E-mail': user.email,
					'Faixa Etária': user.ageCategory === '+18' ? 'Maioridade' : user.ageCategory,
					Perfil: user.roleUser === 'ADMIN' ? 'Administrador' : 'Usuário'
				}));

			case 'games':
				return rawData.map((game) => ({
					'Código de Barras': game.barcode,
					'Nome do Jogo': game.name,
					Categoria: game.category,
					'Mínimo Jogadores': game.minParticipants,
					'Máximo Jogadores': game.maxParticipants
				}));

			case 'events':
				return rawData.map((event) => ({
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

			case 'most_played':
				return rawData.map((item) => ({
					'Título do Jogo': item.gameTitle || item.gameName || '',
					'Total de Empréstimos': item.loanCount || 0
				}));

			case 'event_participants':
				return rawData.map((item) => ({
					'Nome do Evento': item.eventName || '',
					'Total de Participantes': item.participantCount || 0
				}));

			case 'defaulters':
				return rawData.map((item) => ({
					Usuário: item.userName || '',
					'E-mail': item.userEmail || '',
					Jogo: item.gameTitle || '',
					'Data Limite': new Date(item.expectedReturnDate).toLocaleDateString('pt-BR'),
					Atraso: `${item.daysLate || 0} dias`
				}));

			default:
				return [];
		}
	}

	/**
	 * Converte dados mapeados para o formato de arquivo CSV com BOM para compatibilidade com UTF-8.
	 */
	public convertToCSV<T extends object>(data: T[]): string {
		if (data.length === 0) return '';
		const headers = Object.keys(data[0]) as (keyof T)[];
		const csvRows: string[] = [];

		// BOM para compatibilidade UTF-8 no Excel
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

	/**
	 * Converte dados mapeados para uma string JSON formatada.
	 */
	public convertToJSON<T extends object>(data: T[]): string {
		return JSON.stringify(data, null, 2);
	}
}

export const reportService = ReportService.getInstance();
