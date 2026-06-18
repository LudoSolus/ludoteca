<script lang="ts">
	import Fa from 'svelte-fa';
	import type { IconDefinition } from '@fortawesome/free-solid-svg-icons';
	import { goto } from '$app/navigation';

	export let href: string;
	export let title: string;
	export let icon: IconDefinition;

	function handleClick() {
		goto(href);
	}
</script>

<button
	on:click={handleClick}
	class="flex h-32 w-full cursor-pointer flex-col items-center justify-center gap-4 rounded-lg border border-black/20 bg-white p-3 text-center transition-all duration-200 hover:-translate-y-0.5 hover:border-black hover:bg-yellow-50/50 sm:h-36 sm:w-36"
>
	<div
		class="flex h-12 w-12 items-center justify-center rounded-full border border-black bg-yellow-400 text-black shadow-sm"
	>
		<Fa {icon} size="lg" />
	</div>
	<span class="text-xs font-bold text-black">{title}</span>
</button>
