/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.xmldata;

/**
 *
 * @author maex
 */
public interface ModelLecture
{

    public Graphe getGraphe();

    public PlanDeVille getPlan();

    public Demande getDemande();

	public void calculerTournee();
}
