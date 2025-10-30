<script lang="ts">
	import Header from '$lib/components/molecules/Header.svelte';

	let { children } = $props();
</script>

<Header type="user"/>
{@render children?.()}
