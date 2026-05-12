<script lang="ts">
	import '../app.css';
	import favicon from '$lib/assets/favicon.svg';
	import { Toasts } from 'svoast';

	let { children } = $props();
</script>

<svelte:head>
	<link rel="icon" href={favicon} />
	<title>Ludoteca</title>
</svelte:head>

<Toasts position="top-right" />
{@render children?.()}
