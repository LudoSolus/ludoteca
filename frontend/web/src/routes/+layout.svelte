<script lang="ts">
    import '../app.css';
    import favicon from '$lib/assets/favicon.svg';
    import { Toasts } from 'svoast';
</script>

<svelte:head>
    <link rel="icon" href={favicon} />
</svelte:head>

<Toasts position="top-right" />

<main style="padding: 2rem;">
    <slot />
</main>
