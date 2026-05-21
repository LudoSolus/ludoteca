<script lang="ts">
	import Fa, { type IconSize } from 'svelte-fa';
	import type { IconDefinition } from '@fortawesome/free-solid-svg-icons';
	import type { SvelteComponent } from 'svelte';

	// Props
	export let icon: IconDefinition | typeof SvelteComponent | string;
	export let color: string = 'currentColor';
	export let size: number = 32;
	export let classes: string = '';

	$: dataUri = ((): string => {
		if (typeof icon === 'string') {
			if (icon.includes('<svg')) {
				let newIconData = encodeURIComponent(
					icon.replace(/fill=['"](?!url\()([^'"]*)['"]/gi, `fill="${color}"`)
				);
				return `data:image/svg+xml;utf8,${newIconData}`;
			}
			return icon;
		}
		return '';
	})();
</script>

{#if typeof icon === 'function'}
	<svelte:component this={icon} {color} size={`${size}px`} {classes} />
{:else if typeof icon === 'string'}
	<img
		src={dataUri}
		alt={'Icon '.concat(icon)}
		class={classes}
		width={`${size}px`}
		height={`${size}px`}
	/>
{:else}
	<Fa {icon} {color} class={`text-[${size}px] ${classes}`} />
{/if}
