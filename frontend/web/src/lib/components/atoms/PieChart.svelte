<script context="module" lang="ts">
  export interface IChartItem {
    label: string;
    value: number | string;
    color: string;
  }
</script>

<script lang="ts">
  import { onMount } from 'svelte';
  import { Chart, PieController, ArcElement, Tooltip, Legend, Title, type ChartConfiguration } from 'chart.js';
  import ChartDataLabels from 'chartjs-plugin-datalabels';

  // Registra os plugins apenas uma vez
  Chart.register(PieController, ArcElement, Tooltip, Legend, Title, ChartDataLabels);
  
  // PROPS 
  export let items: IChartItem[] = [];
  export let unit: string = ''; 

  let canvasRef: HTMLCanvasElement;
  let chartInstance: Chart;

  $: chartLabels = items.map(item => item.label);
  $: chartValues = items.map(item => Number(item.value) || 0);
  $: chartColors = items.map(item => item.color);

  const config: ChartConfiguration = {
    type: 'pie',
    data: {
      labels: [],
      datasets: [
        {
          data: [],
          backgroundColor: [],
          borderWidth: 0,
        },
      ],
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: { display: false },
        tooltip: { 
          enabled: true, 
          titleFont: {size: 16, weight: 'bold'}, 
          bodyFont: {size: 14, weight: 'bold'},
          callbacks: {
            label: function(context) {
              let label = ' ';
              if (context.raw !== undefined) {
                label += `${context.raw} ${unit}`; 
              }
              return label;
            }
          }
        },
        datalabels: {
          color: '#fff',
          font: { weight: 'bold', size: 10 },
          formatter: (value, ctx) => {
            const label = ctx.chart.data.labels?.[ctx.dataIndex];
            if(Number(value) === 0) return '';
            return `${label}\n${value} ${unit}`;
          },
          textAlign: 'center',
        }
      },
    },
  };

  onMount(() => {
    if (canvasRef) {
      config.data.labels = chartLabels;
      config.data.datasets[0].data = chartValues;
      config.data.datasets[0].backgroundColor = chartColors;
      
      chartInstance = new Chart(canvasRef, config);
    }

    return () => {
      if (chartInstance) chartInstance.destroy();
    };
  });

  $: if (chartInstance && items) {
    chartInstance.data.labels = chartLabels;
    chartInstance.data.datasets[0].data = chartValues;
    chartInstance.data.datasets[0].backgroundColor = chartColors;
    chartInstance.update();
  }
</script>

<div 
  class="card-container flex flex-wrap flex-row items-center justify-between min-h-[250px] w-full gap-4"
>
  <div class="grid grid-cols-[1fr_auto] -start items-start gap-6 pl-0">
    <ul class="flex flex-col gap-3 w-full">
      {#each items as item}
        {#if item.value}
          <li class="flex items-start gap-3">
            <span class="w-3 h-3 rounded-full" style="background-color: {item.color};"></span>
            <span class="text-gray-600 font-medium text-sm">{item.label}</span>
          </li>
        {/if}
      {/each}
    </ul>
  </div>

  <div class="relative w-full max-w-[14rem] max-h-[14rem] flex-shrink-0 flex items-center justify-center cursor-pointer">
    <canvas bind:this={canvasRef}></canvas>
  </div>

</div>

<style>
  .card-container {
    background-color: transparent;
  }
</style>