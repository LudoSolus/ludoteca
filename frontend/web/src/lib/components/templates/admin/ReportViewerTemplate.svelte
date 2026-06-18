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
		faInbox
	} from '@fortawesome/free-solid-svg-icons';
	import GoBack from '$lib/components/molecules/GoBack.svelte';
	import Button from '$lib/components/atoms/Button.svelte';
	import type { TReportType, TReportFormat } from '$lib/types/report.type';

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
		}
	};

	$: currentMeta = reportMeta[reportType];
</script>

<main class="flex w-full flex-col gap-6 px-4 py-6 sm:px-10 xl:px-15">
	<GoBack title={currentMeta.title} description={currentMeta.description} />

	<!-- Barra de Ações -->
	<div
		class="flex flex-col gap-4 -mt-2 rounded-2xl border border-black/10 bg-[#FFF6C4]/30 p-4 shadow-sm sm:flex-row sm:items-center sm:justify-between"
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

	<!-- Tabela de Conteúdo -->
	<div class="w-full mt-2">
		{#if data === null}
			<!-- Skeleton Loading State -->
			<div class="w-full rounded-2xl border-2 border-black bg-white overflow-hidden shadow-md">
				<div class="flex bg-yellow-400 p-4 border-b-2 border-black">
					<div class="skeleton-pulse h-5 w-1/4 rounded bg-black/20"></div>
					<div class="skeleton-pulse h-5 w-1/4 rounded bg-black/20 ml-auto"></div>
					<div class="skeleton-pulse h-5 w-1/4 rounded bg-black/20 ml-auto"></div>
				</div>
				{#each Array(5) as _}
					<div class="flex p-4 border-b border-gray-100 items-center">
						<div class="skeleton-pulse h-4 w-1/3 rounded bg-gray-200"></div>
						<div class="skeleton-pulse h-4 w-1/4 rounded bg-gray-200 ml-auto"></div>
						<div class="skeleton-pulse h-4 w-1/5 rounded bg-gray-200 ml-auto"></div>
					</div>
				{/each}
			</div>
		{:else if data.length === 0}
			<!-- Empty State -->
			<div
				class="flex w-full flex-col items-center justify-center gap-3 rounded-2xl border-2 border-dashed border-black p-10 text-center bg-white shadow-[0px_4px_10px_rgba(0,0,0,0.15)]"
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
				class="w-full overflow-x-auto rounded-2xl border-2 border-black bg-white shadow-[0px_4px_10px_rgba(0,0,0,0.15)]"
			>
				<table class="w-full min-w-[600px] border-collapse text-left text-sm text-black">
					<thead>
						<tr class="bg-yellow-400 border-b-2 border-black select-none">
							{#if reportType === 'users'}
								<th class="p-4 font-bold border-r border-black/20">ID</th>
								<th class="p-4 font-bold border-r border-black/20">E-mail</th>
								<th class="p-4 font-bold border-r border-black/20">Faixa Etária</th>
								<th class="p-4 font-bold">Perfil</th>
							{:else}
								<!-- games -->
								{#if reportType === 'games'}
									<th class="p-4 font-bold border-r border-black/20">ID</th>
									<th class="p-4 font-bold border-r border-black/20">Código de Barras</th>
									<th class="p-4 font-bold border-r border-black/20">Nome do Jogo</th>
									<th class="p-4 font-bold border-r border-black/20">Categoria</th>
									<th class="p-4 font-bold">Limites de Jogadores</th>
								{:else}
									<!-- events -->
									{#if reportType === 'events'}
										<th class="p-4 font-bold border-r border-black/20">Nome do Evento</th>
										<th class="p-4 font-bold border-r border-black/20">Status</th>
										<th class="p-4 font-bold border-r border-black/20">Período</th>
										<th class="p-4 font-bold border-r border-black/20">Endereço</th>
										<th class="p-4 font-bold">Atividades</th>
									{:else}
										<!-- most_played -->
										{#if reportType === 'most_played'}
											<th class="p-4 font-bold border-r border-black/20">Título do Jogo</th>
											<th class="p-4 font-bold">Total de Empréstimos</th>
										{:else}
											<!-- event_participants -->
											{#if reportType === 'event_participants'}
												<th class="p-4 font-bold border-r border-black/20">Nome do Evento</th>
												<th class="p-4 font-bold">Total de Participantes</th>
											{/if}
										{/if}
									{/if}
								{/if}
							{/if}
						</tr>
					</thead>
					<tbody>
						{#each data as item}
							<tr class="border-b border-black/10 hover:bg-yellow-50/30 transition-colors">
								{#if reportType === 'users'}
									<td class="p-4 border-r border-black/10 font-mono text-xs">{item.id}</td>
									<td class="p-4 border-r border-black/10">{item.email}</td>
									<td class="p-4 border-r border-black/10">
										{item.ageCategory === '+18' ? 'Maioridade' : item.ageCategory}
									</td>
									<td class="p-4">
										{item.roleUser === 'ADMIN' ? 'Administrador' : 'Usuário'}
									</td>
								{:else}
									<!-- games -->
									{#if reportType === 'games'}
										<td class="p-4 border-r border-black/10 font-mono text-xs">{item.id}</td>
										<td class="p-4 border-r border-black/10">{item.barcode}</td>
										<td class="p-4 border-r border-black/10 font-medium">{item.name}</td>
										<td class="p-4 border-r border-black/10">{item.category}</td>
										<td class="p-4">{item.minParticipants} a {item.maxParticipants} jogadores</td>
									{:else}
										<!-- events -->
										{#if reportType === 'events'}
											<td class="p-4 border-r border-black/10 font-medium">{item.name}</td>
											<td class="p-4 border-r border-black/10">
												<span
													class="px-2 py-0.5 rounded-full text-xs font-semibold border {item.status ===
													'SCHEDULED'
														? 'bg-blue-100 text-blue-800 border-blue-200'
														: item.status === 'INPROGRESS'
															? 'bg-yellow-100 text-yellow-800 border-yellow-200'
															: 'bg-green-100 text-green-800 border-green-200'}"
												>
													{item.status === 'SCHEDULED'
														? 'Agendado'
														: item.status === 'INPROGRESS'
															? 'Em Andamento'
															: 'Finalizado'}
												</span>
											</td>
											<td class="p-4 border-r border-black/10 text-xs">
												<p><strong>De:</strong> {new Date(item.startDate).toLocaleString('pt-BR')}</p>
												<p><strong>Até:</strong> {new Date(item.finalDate).toLocaleString('pt-BR')}</p>
											</td>
											<td class="p-4 border-r border-black/10 text-xs leading-relaxed max-w-[200px]">
												{item.street}, {item.number} - {item.neighborhood}, {item.city}/{item.state}
											</td>
											<td class="p-4 text-xs">
												<div class="flex flex-col gap-1">
													{#if item.hasBoardGames}
														<span class="flex items-center gap-1.5 text-green-700 font-medium">✔ Tabuleiro</span>
													{/if}
													{#if item.hasRpg}
														<span class="flex items-center gap-1.5 text-green-700 font-medium">✔ RPG</span>
													{/if}
													{#if item.hasEscapeRoom}
														<span class="flex items-center gap-1.5 text-green-700 font-medium">✔ Escape Room</span>
													{/if}
												</div>
											</td>
										{:else}
											<!-- most_played -->
											{#if reportType === 'most_played'}
												<td class="p-4 border-r border-black/10 font-medium">
													{item.gameTitle || item.gameName || ''}
												</td>
												<td class="p-4 font-bold text-center sm:text-left">
													{item.loanCount || 0} partidas
												</td>
											{:else}
												<!-- event_participants -->
												{#if reportType === 'event_participants'}
													<td class="p-4 border-r border-black/10 font-medium">{item.eventName || ''}</td>
													<td class="p-4 font-bold text-center sm:text-left">
														{item.participantCount || 0} participantes
													</td>
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
