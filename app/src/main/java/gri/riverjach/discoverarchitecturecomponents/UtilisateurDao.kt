package gri.riverjach.discoverarchitecturecomponents

import androidx.lifecycle.LiveData
import androidx.room.*

data class NameAgeTuple(val name: String, val age: Int)

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

    /**
     * dao.getAllUtilisateurs().observe(this, Observer { utilisateurs -> ... })
     */
    @Query("SELECT * FROM utilisateur")
    fun getAllUtilisateurs(): LiveData<List<Utilisateur>>

    /**
     * dao.getUserById(3).observe(this, Observer { utilisateur -> ... })
     * on a LiveData car room fait de l'asynchrone donc on observe.
     */
    @Query("SELECT * FROM utilisateur WHERE id= :id")
    fun getUtilisateurById(id: Int): LiveData<Utilisateur>

    @Query("SELECT * FROM utilisateur WHERE name LIKE :name AND age > :minAge")
    fun getUtilisateursByNameAndOlderThan(name: String, minAge: Int): LiveData<List<Utilisateur>>

    @Query("SELECT * FROM utilisateur WHERE name IN (:name)")
    fun getUtilisateursFromNames(name: List<String>): LiveData<List<Utilisateur>>

    @Query("SELECT name, age FROM utilisateur")
    fun getNameAgeTuple(): LiveData<List<NameAgeTuple>>

}