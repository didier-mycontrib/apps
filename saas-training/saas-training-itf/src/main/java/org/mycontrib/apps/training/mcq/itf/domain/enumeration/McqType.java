package org.mycontrib.apps.training.mcq.itf.domain.enumeration;
/**
 * 
 * @author didier Defrance
 * 
 * FREE : entraînement libre (aucun enregistrement de resultat) : pour apprentissage ou test pre_requis non officiel
 * VALID : pour valider les acquis du module N (ou pré requis du module N+1) sans démarche officielle
 *         (enregistrement que du score si user non générique).
 *         peut éventuellement être effectuer plusieurs fois (validation constructive)
 * CERTIFICATION : certification/validation officielle : ne peut idéalement être utilisé/effectué qu'une seule fois.
 *                 enregistrement complet des résultats
 *                 
 *  On pourra envisager les rétrogradations classiques suivantes:
 *         CERTIFICATION --> VALID ou FREE (une fois la session certifiante effectuée)
 *         VALID --> FREE (au bout d'une longue période)               
 */
public enum McqType {
FREE,VALID,CERTIFICATION
} 


