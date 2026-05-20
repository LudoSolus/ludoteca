<script lang="ts">
	import HistoryCard from '$lib/components/molecules/HistoryCard.svelte';

	type HistoryType = 'rpg' | 'boardgame' | 'escape';

	interface HistoryItem {
		title: string;
		type: HistoryType;
		timePlayed?: string;
		date?: string;
		system?: string;
		sessions?: number;
	}

	const historyItems: HistoryItem[] = [
		{ title: 'A era glacial de ragnar', type: 'rpg', system: 'DezD', sessions: 5 },
		{ title: 'WAR', type: 'boardgame', timePlayed: '92 min', date: '22/10' },
		{ title: 'Fuja da sala', type: 'escape', timePlayed: '92 min', date: '22/10' },
		{ title: 'Banco imobiliário', type: 'boardgame', timePlayed: '92 min', date: '22/10' }
	];
</script>

<main class="relative">
	<h1 class="font-inknut absolute top-32 left-10 z-10 text-5xl text-black">Tempo Jogado</h1>

	<aside
		class="absolute top-32 right-0
           flex
           h-[85vh]
           w-[800px]
           flex-col gap-8
           overflow-y-auto
           rounded-xl
           border border-black
           bg-[#fff2a8] px-12 py-10
           shadow-[0_6px_0_rgba(0,0,0,0.3)]"
	>
		<h2 class="font-inknut mb-4 ml-2 self-start text-4xl">Histórico</h2>

		<div class="mx-auto flex w-full max-w-[800px] flex-col gap-8">
			{#each historyItems as item}
				<HistoryCard
					title={item.title}
					type={item.type}
					timePlayed={item.timePlayed}
					date={item.date}
					system={item.system}
					sessions={item.sessions}
				/>
			{/each}
		</div>
	</aside>
</main>
