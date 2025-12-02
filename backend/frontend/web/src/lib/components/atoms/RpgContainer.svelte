<script lang="ts">
	import Fa from 'svelte-fa';
	import { faCalendar as faCalendarRegular } from '@fortawesome/free-regular-svg-icons';
	import diceIcon from '$lib/assets/dice-twenty-faces-one-svgrepo-com.svg';
	import Button from '$lib/components/atoms/Button.svelte';
	import { faArrowRight } from '@fortawesome/free-solid-svg-icons';

	export let title: string = '';
	export let system: string = '';
	export let startDate: Date;

	$: formattedDate = startDate?.toLocaleDateString('pt-BR', {
		day: '2-digit',
		month: '2-digit',
		year: 'numeric'
	});
</script>

<div
  class="bg-yellow-100 border-2 border-black rounded-xl p-4 shadow-md 
         flex flex-col gap-14 w-full max-w-lg transition-all duration-300 hover:shadow-lg"
>

	<div class="-ml-2 flex flex-col gap-8">
		<h2 class="text-3xl font-semibold text-black">{title}</h2>

    <div class="flex justify-between items-center">
			<div class="flex flex-col gap-3">
				<div class="flex items-center gap-2 text-xl font-medium text-black">
          <img src={diceIcon} alt="Ícone de dado" class="w-6 h-6 object-contain align-middle" />
					<span class="leading-none">Sistema: {system}</span>
				</div>

				<div class="flex items-center gap-2 text-xl font-medium text-black">
          <Fa icon={faCalendarRegular} class="w-6 h-6 align-middle" />
          <span class="leading-none">Início: {formattedDate}</span>
				</div>
			</div>

			<Button text="Detalhes" onClick={() => {}} rightIcon={faArrowRight} />
		</div>
	</div>
</div>
