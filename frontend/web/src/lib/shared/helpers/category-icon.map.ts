import type { SvelteComponent } from 'svelte';
import type { IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { 
  faChess,
  faHandshake,
  faMasksTheater,
  faPuzzlePiece,
  faMagnifyingGlass,
  faBolt,
  faGamepad,

} from '@fortawesome/free-solid-svg-icons';

import { ECategory } from '$lib/shared/enums/category.enum';

import PersonDiceIcon from '$lib/components/atoms/icons/PersonDiceIcon.svelte';
import AbstractCubeIcon from '$lib/components/atoms/icons/AbstractCubeIcon.svelte';
import MapAdventureIcon from '$lib/components/atoms/icons/MapAdventureIcon.svelte';
import ComercialIcon from '$lib/components/atoms/icons/ComercialIcon.svelte';

export const categoryIconMap: Record<ECategory, IconDefinition | typeof SvelteComponent | string> = {
  [ECategory.STRATEGY]: faChess,
  [ECategory.COOPERATIVE]: faHandshake,
  [ECategory.DEDUCTION]: faMagnifyingGlass,
  [ECategory.PARTY_GAME]: PersonDiceIcon as typeof SvelteComponent,
  [ECategory.BLUFF_DECEPTION]: faMasksTheater,
  [ECategory.DEXTERITY]: faBolt,
  [ECategory.ABSTRACT_GAMES]: AbstractCubeIcon as typeof SvelteComponent,
  [ECategory.LOGIC_PUZZLE]: faPuzzlePiece,
  [ECategory.ADVENTURE_THEME]: MapAdventureIcon as typeof SvelteComponent,
  [ECategory.NEGOTIATION]: ComercialIcon as typeof SvelteComponent,
  [ECategory.OTHER]: faGamepad
}