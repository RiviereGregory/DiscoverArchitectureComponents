package gri.riverjach.discoverarchitecturecomponents

import androidx.room.*

@Dao
interface UtilisateurDao {

    /**
     * Usage:
     * dao.insertUtilisateur(utilisateur)
     */
    @Insert
    fun insertUtilisateur(utilisateur: Utilisateur)

    /**
     * Usage:
     * val id = dao.insertUtilisateur(utilisateur)
     */
    @Insert
    fun inssertUtilisateurReturnId(utilisateur: Utilisateur): Long

    /**
     * Usage:
     * utilisateurs = arrayOf(utilisateur1, utilisateur2, utilisateur3)
     * val ids = dao.insertUtilisateur(utilisateurs)
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUtilisateurs(utilisateurs: List<Utilisateur>): List<Long>

    /**
     * Usage:
     * val ids = dao.insertUtilisateur(utilisateur1, utilisateur2, utilisateur3)
     */
    @Insert
    fun insertUtilisateurs(vararg utilisateurs: Utilisateur): Array<Int>

    /**
     * Usage:
     * dao.updateUtilisateur(utilisateur)
     */
    @Update
    fun updateUtilisateur(utilisateur: Utilisateur)

    /**
     * Usage:
     * val rows = dao.updateUtilisateurs(utilisateur1, utilisateur2)
     */
    @Update
    fun updateUtilisateurs(vararg utilisateurs: Utilisateur): Array<Int>

    @Delete
    fun deleteUtilisateur(utilisateur: Utilisateur): Int

    /**
     * Usage:
     * val rows = dao.deleteUtilisateurs(utilisateur1, utilisateur2)
     */
    @Delete
    fun deleteUtilisateurs(vararg utilisateurs: Utilisateur): Array<Int>
}