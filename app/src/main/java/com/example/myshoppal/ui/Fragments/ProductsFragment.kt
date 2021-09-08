package com.example.myshoppal.ui.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myshoppal.R
import com.example.myshoppal.databinding.FragmentProductsBinding
import com.example.myshoppal.firestore.FirestoreClass
import com.example.myshoppal.models.Product
import com.example.myshoppal.ui.activities.AddProductActivity
import com.example.myshoppal.ui.activities.SettingsActivity

class ProductsFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    //private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentProductsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    fun successProductListFromFirestore(productsList: ArrayList<Product>) {
        hideProgressDialog()

        for(i in productsList){
            Log.i("Products list:", i.title)
        }

    }

    private fun getProductListFromFireStore() {
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getProductsList(this@ProductsFragment)
    }

    override fun onResume() {
        super.onResume()

        getProductListFromFireStore()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_product_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {

            R.id.action_add_product -> {
                startActivity(Intent(activity, AddProductActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}