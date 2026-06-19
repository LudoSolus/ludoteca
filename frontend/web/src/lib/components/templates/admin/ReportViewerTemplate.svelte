<script lang="ts">
	import Fa from 'svelte-fa';
	import {
		faFileCsv,
		faFileCode,
		faFilePdf,
		faUsers,
		faDice,
		faCalendar,
		faChartLine,
		faUserGroup,
		faInbox,
		faClock,
		faChartBar
	} from '@fortawesome/free-solid-svg-icons';
	import GoBack from '$lib/components/molecules/GoBack.svelte';
	import Button from '$lib/components/atoms/Button.svelte';
	import type { TReportType, TReportFormat } from '$lib/types/report.type';

	const MONTH_NAMES = [
		'Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
		'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'
	];

	export let reportType: TReportType;
	export let data: any[] | null;
	export let exporting: boolean = false;
	export let onExport: (format: TReportFormat) => void;

	const reportMeta = {
		users: {
			title: 'Relatório de Usuários Cadastrados',
			description: 'Tabela detalhada de todos os usuários registrados no sistema.',
			icon: faUsers
		},
		games: {
			title: 'Relatório de Jogos do Acervo',
			description: 'Listagem de jogos de tabuleiro, códigos de barra e capacidade de jogadores.',
			icon: faDice
		},
		events: {
			title: 'Relatório de Eventos',
			description: 'Histórico de eventos realizados e agendados com locais e atividades.',
			icon: faCalendar
		},
		most_played: {
			title: 'Relatório de Jogos Mais Jogados',
			description: 'Estatísticas completas de empréstimos e popularidade de jogos.',
			icon: faChartLine
		},
		event_participants: {
			title: 'Relatório de Engajamento em Eventos',
			description: 'Métricas de participação e presenças registradas em cada evento.',
			icon: faUserGroup
		},
		defaulters: {
			title: 'Relatório de Atrasos e Devoluções',
			description: 'Listagem de usuários com devoluções de jogos pendentes e dias de atraso.',
			icon: faClock
		},
		seasonality: {
			title: 'Relatório de Empréstimos por Período',
			description: 'Análise de sazonalidade com o total de empréstimos consolidados por mês e ano.',
			icon: faChartBar
		}
	};

	$: currentMeta = reportMeta[reportType];
</script>

<main class="flex w-full flex-col gap-6 px-4 py-6 sm:px-10 xl:px-15">
	<GoBack title={currentMeta.title} description={currentMeta.description} />

	<div
		class="-mt-2 flex flex-col gap-4 rounded-lg border border-black/10 p-4 sm:flex-row sm:items-center sm:justify-between"
	>
		<div class="flex items-center gap-3">
			<div
				class="flex h-8 w-8 items-center justify-center rounded-full border border-black bg-yellow-400 text-black"
			>
				<Fa icon={currentMeta.icon} />
			</div>
			<span class="text-sm font-bold text-gray-800">Exportar dados deste relatório:</span>
		</div>
		<div class="flex flex-wrap items-center gap-3">
			<Button
				text="Exportar CSV"
				leftIcon={faFileCsv}
				variant="secondary"
				onClick={() => onExport('csv')}
				disabled={exporting || !data || data.length === 0}
			/>
			<Button
				text="Exportar JSON"
				leftIcon={faFileCode}
				variant="secondary"
				onClick={() => onExport('json')}
				disabled={exporting || !data || data.length === 0}
			/>
			<Button
				text="Imprimir PDF"
				leftIcon={faFilePdf}
				onClick={() => onExport('pdf')}
				disabled={exporting || !data || data.length === 0}
			/>
		</div>
	</div>

	<div class="mt-2 w-full">
		{#if data === null}
			<div class="w-full overflow-hidden rounded-lg border-2 border-black bg-white shadow-md">
				<div class="flex border-b-2 border-black bg-yellow-400 p-4">
					<div class="skeleton-pulse h-5 w-1/4 rounded bg-black/20"></div>
					<div class="skeleton-pulse ml-auto h-5 w-1/4 rounded bg-black/20"></div>
					<div class="skeleton-pulse ml-auto h-5 w-1/4 rounded bg-black/20"></div>
				</div>
				{#each Array(5) as _}
					<div class="flex items-center border-b border-gray-100 p-4">
						<div class="skeleton-pulse h-4 w-1/3 rounded bg-gray-200"></div>
						<div class="skeleton-pulse ml-auto h-4 w-1/4 rounded bg-gray-200"></div>
						<div class="skeleton-pulse ml-auto h-4 w-1/5 rounded bg-gray-200"></div>
					</div>
				{/each}
			</div>
		{:else if data.length === 0}
			<div
				class="flex w-full flex-col items-center justify-center gap-3 rounded-lg border-2 border-dashed border-black bg-white p-10 text-center shadow-[0px_4px_10px_rgba(0,0,0,0.15)]"
			>
				<div
					class="flex h-12 w-12 items-center justify-center rounded-full border-2 border-black bg-yellow-400 text-black"
				>
					<Fa icon={faInbox} size="lg" />
				</div>
				<div class="flex flex-col gap-1">
					<h4 class="inknut text-base font-bold">Nenhum registro encontrado</h4>
					<p class="text-xs text-gray-600">
						Não há dados disponíveis para este relatório no momento.
					</p>
				</div>
			</div>
		{:else}
			<!-- Renderização de Tabelas Específicas -->
			<div
				class="w-full overflow-x-auto rounded-lg border-2 border-black bg-white shadow-[0px_4px_10px_rgba(0,0,0,0.15)]"
			>
				<table
					id="report-table"
					class="w-full min-w-[600px] border-collapse text-left text-sm text-black"
				>
					<thead>
						<tr class="border-b-2 border-black bg-yellow-400 select-none">
							{#if reportType === 'users'}
								<th class="border-r border-black/20 p-4 font-bold">E-mail</th>
								<th class="border-r border-black/20 p-4 font-bold">Faixa Etária</th>
								<th class="p-4 font-bold">Perfil</th>
							{:else}
								<!-- games -->
								{#if reportType === 'games'}
									<th class="border-r border-black/20 p-4 font-bold">Código de Barras</th>
									<th class="border-r border-black/20 p-4 font-bold">Nome do Jogo</th>
									<th class="border-r border-black/20 p-4 font-bold">Categoria</th>
									<th class="p-4 font-bold">Limites de Jogadores</th>
								{:else}
									<!-- events -->
									{#if reportType === 'events'}
										<th class="border-r border-black/20 p-4 font-bold">Nome do Evento</th>
										<th class="border-r border-black/20 p-4 font-bold">Status</th>
										<th class="border-r border-black/20 p-4 font-bold">Período</th>
										<th class="border-r border-black/20 p-4 font-bold">Endereço</th>
										<th class="p-4 font-bold">Atividades</th>
									{:else}
										<!-- most_played -->
										{#if reportType === 'most_played'}
											<th class="border-r border-black/20 p-4 font-bold">Título do Jogo</th>
											<th class="p-4 font-bold">Total de Empréstimos</th>
										{:else}
											<!-- event_participants -->
											{#if reportType === 'event_participants'}
												<th class="border-r border-black/20 p-4 font-bold">Nome do Evento</th>
												<th class="p-4 font-bold">Total de Participantes</th>
											{:else}
												<!-- defaulters -->
												{#if reportType === 'defaulters'}
													<th class="border-r border-black/20 p-4 font-bold">Usuário</th>
													<th class="border-r border-black/20 p-4 font-bold">E-mail</th>
													<th class="border-r border-black/20 p-4 font-bold">Jogo</th>
													<th class="border-r border-black/20 p-4 font-bold">Data Limite</th>
													<th class="p-4 font-bold">Atraso</th>
												{:else}
													<!-- seasonality -->
													{#if reportType === 'seasonality'}
														<th class="border-r border-black/20 p-4 font-bold">Ano</th>
														<th class="border-r border-black/20 p-4 font-bold">Mês</th>
														<th class="p-4 font-bold">Total de Empréstimos</th>
													{/if}
												{/if}
											{/if}
										{/if}
									{/if}
								{/if}
							{/if}
						</tr>
					</thead>
					<tbody>
						{#each data as item}
							<tr class="border-b border-black/10 transition-colors hover:bg-yellow-50/30">
								{#if reportType === 'users'}
									<td class="border-r border-black/10 p-4">{item.email}</td>
									<td class="border-r border-black/10 p-4">
										{item.ageCategory === '+18' ? 'Maioridade' : item.ageCategory}
									</td>
									<td class="p-4">
										{item.roleUser === 'ADMIN' ? 'Administrador' : 'Usuário'}
									</td>
								{:else}
									<!-- games -->
									{#if reportType === 'games'}
										<td class="border-r border-black/10 p-4">{item.barcode}</td>
										<td class="border-r border-black/10 p-4 font-medium">{item.name}</td>
										<td class="border-r border-black/10 p-4">{item.category}</td>
										<td class="p-4">{item.minParticipants} a {item.maxParticipants} jogadores</td>
									{:else}
										<!-- events -->
										{#if reportType === 'events'}
											<td class="border-r border-black/10 p-4 font-medium">{item.name}</td>
											<td class="border-r border-black/10 p-4">
												<span
													class="rounded-full border px-2 py-0.5 text-xs font-semibold {item.status ===
													'SCHEDULED'
														? 'border-blue-200 bg-blue-100 text-blue-800'
														: item.status === 'INPROGRESS'
															? 'border-yellow-200 bg-yellow-100 text-yellow-800'
															: 'border-green-200 bg-green-100 text-green-800'}"
												>
													{item.status === 'SCHEDULED'
														? 'Agendado'
														: item.status === 'INPROGRESS'
															? 'Em Andamento'
															: 'Finalizado'}
												</span>
											</td>
											<td class="border-r border-black/10 p-4 text-xs">
												<p>
													<strong>De:</strong>
													{new Date(item.startDate).toLocaleString('pt-BR')}
												</p>
												<p>
													<strong>Até:</strong>
													{new Date(item.finalDate).toLocaleString('pt-BR')}
												</p>
											</td>
											<td
												class="max-w-[200px] border-r border-black/10 p-4 text-xs leading-relaxed"
											>
												{item.street}, {item.number} - {item.neighborhood}, {item.city}/{item.state}
											</td>
											<td class="p-4 text-xs">
												<div class="flex flex-col gap-1">
													{#if item.hasBoardGames}
														<span class="flex items-center gap-1.5 font-medium text-green-700"
															>✔ Tabuleiro</span
														>
													{/if}
													{#if item.hasRpg}
														<span class="flex items-center gap-1.5 font-medium text-green-700"
															>✔ RPG</span
														>
													{/if}
													{#if item.hasEscapeRoom}
														<span class="flex items-center gap-1.5 font-medium text-green-700"
															>✔ Escape Room</span
														>
													{/if}
												</div>
											</td>
										{:else}
											<!-- most_played -->
											{#if reportType === 'most_played'}
												<td class="border-r border-black/10 p-4 font-medium">
													{item.gameTitle || item.gameName || ''}
												</td>
												<td class="p-4 text-center font-bold sm:text-left">
													{item.loanCount || 0} partidas
												</td>
											{:else}
												<!-- event_participants -->
												{#if reportType === 'event_participants'}
													<td class="border-r border-black/10 p-4 font-medium"
														>{item.eventName || ''}</td
													>
													<td class="p-4 text-center font-bold sm:text-left">
														{item.participantCount || 0} participantes
													</td>
												{:else}
													<!-- defaulters -->
													{#if reportType === 'defaulters'}
														<td class="border-r border-black/10 p-4 font-medium">{item.userName || ''}</td>
														<td class="border-r border-black/10 p-4 font-mono text-xs">{item.userEmail || ''}</td>
														<td class="border-r border-black/10 p-4">{item.gameTitle || ''}</td>
														<td class="border-r border-black/10 p-4 text-xs">
															{new Date(item.expectedReturnDate).toLocaleDateString('pt-BR')}
														</td>
														<td class="p-4 font-bold text-red-600">
															{item.daysLate || 0} dias
														</td>
													{:else}
														<!-- seasonality -->
														{#if reportType === 'seasonality'}
															<td class="border-r border-black/10 p-4 font-medium">{item.year}</td>
															<td class="border-r border-black/10 p-4">
																{MONTH_NAMES[item.month - 1] || item.month}
															</td>
															<td class="p-4 font-bold">
																{item.totalLoans} empréstimos
															</td>
														{/if}
													{/if}
												{/if}
											{/if}
										{/if}
									{/if}
								{/if}
							</tr>
						{/each}
					</tbody>
				</table>
			</div>
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
