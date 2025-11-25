<script lang="ts">
	import type { IconDefinition } from '@fortawesome/free-solid-svg-icons';;
	import Icon from './icons/Icon.svelte';

	export let icon: IconDefinition | string;
	export let title: string;
</script>

<div
	class="flex items-center gap-4 font-medium"
	style="font-size:18px; line-height:22px; letter-spacing:0;"
>
	<Icon {icon} classes="h-5 w-5 text-black" />
	<span>{title}</span>
</div>
