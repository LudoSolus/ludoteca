<script lang="ts">
  import { onMount } from 'svelte';
  import { Chart, PieController, ArcElement, Tooltip, Legend, Title, type ChartConfiguration } from 'chart.js';
  import ChartDataLabels from 'chartjs-plugin-datalabels';

  Chart.register(PieController, ArcElement, Tooltip, Legend, Title, ChartDataLabels);

  export let escapeTime: string;
  export let boardGamesTime: string;
  export let rpgTime: string;
  export let onClick: () => void = () => {};

  let canvasRef: HTMLCanvasElement;
  let chartInstance: Chart;

  $: dataValues = [
    Number(boardGamesTime) || 0, 
    Number(rpgTime) || 0,        
    Number(escapeTime) || 0      
  ];

  const config: ChartConfiguration = {
    type: 'pie',
    data: {
      labels: ['Board Games', 'RPG', 'Escape Room'],
      datasets: [
        {
          data: [],
          backgroundColor: ['#7C8DFF', '#00E699', '#A855F7'],
          borderWidth: 0,
        },
      ],
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: { display: false },
        tooltip: { enabled: true },
        datalabels: {
          color: '#fff',
          font: { weight: 'normal', size: 10 },
          formatter: (value, ctx) => {
            const label = ctx.chart.data.labels?.[ctx.dataIndex];
            if(Number(value) === 0) return '';
            return `${label}\n${value}`;
          },
          textAlign: 'center',
        }
      },
    },
  };

  
  onMount(() => {
    if (canvasRef) {
      config.data.datasets[0].data = dataValues;
      
      chartInstance = new Chart(canvasRef, config);
    }

    return () => {
      if (chartInstance) chartInstance.destroy();
    };
  });

  $: if (chartInstance && dataValues) {
    chartInstance.data.datasets[0].data = dataValues;
    chartInstance.update();
  }
</script>


<div 
  class="card-container flex flex-col md:flex-row items-center justify-between min-h-[250px] w-full p-6 gap-4"
  on:click={onClick}
  role="button"
  tabindex="0"
>
  
  <div class="flex flex-col h-full justify-center md:justify-start items-start w-full md:w-1/2 gap-6 pl-2 md:pl-4">
    <ul class="flex flex-col gap-3 w-full">
      <li class="flex items-center gap-3">
        <span class="w-3 h-3 rounded-full bg-[#7C8DFF]"></span>
        <span class="text-gray-600 font-medium text-sm">Board Games</span>
      </li>
      <li class="flex items-center gap-3">
        <span class="w-3 h-3 rounded-full bg-[#00E699]"></span>
        <span class="text-gray-600 font-medium text-sm">RPG</span>
      </li>
      <li class="flex items-center gap-3">
        <span class="w-3 h-3 rounded-full bg-[#A855F7]"></span>
        <span class="text-gray-600 font-medium text-sm">Escape Room</span>
      </li>
    </ul>
  </div>

  <div class="relative w-full md:w-1/2 h-56 flex items-center justify-start lg:justify-center cursor-pointer">
    <canvas bind:this={canvasRef}></canvas>
  </div>

</div>

<style>
  .card-container {
    background-color: transparent;
  }
</style>