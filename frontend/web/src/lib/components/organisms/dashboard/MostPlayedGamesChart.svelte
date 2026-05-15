<script lang="ts">
	import { onMount } from 'svelte';
	import Chart from 'chart.js/auto';
	import type { IMostPlayedGamesData } from '$lib/api/queries/dashboard/most-played-games/most-played-games.interface';

	export let data: IMostPlayedGamesData[] = [];
	let canvas: HTMLCanvasElement;
	let chart: Chart;

	onMount(() => {
		const ctx = canvas.getContext('2d');
		if (!ctx) return;

		chart = new Chart(ctx, {
			type: 'bar',
			data: {
				labels: data.map(d => d.gameTitle),
				datasets: [
					{
						label: 'Quantidade de Empréstimos/Jogadas',
						data: data.map((d) => d.loanCount),
						backgroundColor: 'rgba(147, 51, 234, 0.6)',
						borderColor: 'rgba(147, 51, 234, 1)',
						borderWidth: 1,
						borderRadius: 4
					}
				]
			},
			options: {
				responsive: true,
				maintainAspectRatio: false,
				plugins: {
					legend: {
						display: false
					},
					title: {
						display: true,
						text: 'Jogos Mais Jogados',
						font: {
							size: 16,
							family: "'Inter', sans-serif"
						}
					}
				},
				scales: {
					y: {
						beginAtZero: true,
						ticks: {
							stepSize: 1
						}
					}
				}
			}
		});

		return () => {
			if (chart) chart.destroy();
		};
	});
	
	// Reactivity for data updates
	$: if (chart && data && data.length > 0) {
		chart.data.labels = data.map(d => d.gameTitle);
		chart.data.datasets[0].data = data.map(d => d.loanCount);
		chart.update();
	}
</script>

<div class="h-80 w-full rounded-lg border border-gray-200 bg-transparent p-4">
	<canvas bind:this={canvas}></canvas>
</div>
