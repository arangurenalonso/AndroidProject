package com.example.proyectoandroid._4view.categoria

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.proyectoandroid.R
import com.example.proyectoandroid.databinding.FragmentCategoryBinding
import com.example.proyectoandroid._4view.categoria.adapter.CategoryAdapter
import com.example.proyectoandroid._1data.AppDataBase
import com.example.proyectoandroid._1data.local.categoryproduct.LocalCategoryProductoSource
import com.example.proyectoandroid._1data.model.CategoriaProductoEntity
import com.example.proyectoandroid._2repository.category.CategoryProductRepositoryImp
import com.example.proyectoandroid._3viewmodel.CategoryProductViewModel
import com.example.proyectoandroid._3viewmodel.CategoryProductoViewModelFactory
import com.example.proyectoandroid._0core.Result

class CategoryFragment : Fragment(), CategoryAdapter.AdaptadorCategoryProductoInterface {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var categoriaAdapter: CategoryAdapter
    private lateinit var gridLayoutManager: GridLayoutManager

    private val viewModel by viewModels<CategoryProductViewModel> {
        CategoryProductoViewModelFactory(
            CategoryProductRepositoryImp(
                LocalCategoryProductoSource(AppDataBase.getInstanceDatabase(requireContext()).categoryProductDao()),
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onCategoryClick(categoriaProductoEntity: CategoriaProductoEntity) {
        //Toast.makeText(context,"Categoria Seleccionada ${categoriaProductoEntity.nombre_categ}",Toast.LENGTH_SHORT).show()
        val action = CategoryFragmentDirections.actionCategoryFragmentToProductoFragment(categoriaProductoEntity.id_categ)
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding= FragmentCategoryBinding.bind(view)

        viewModel.fetchGetAllCategories().observe(viewLifecycleOwner,{ result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    categoriaAdapter= CategoryAdapter(result.data,this)
                    gridLayoutManager = GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,false)

                    binding.rvCategoryProducto.apply {
                        layoutManager=gridLayoutManager
                        setHasFixedSize(true)
                        adapter=categoriaAdapter
                    }

                }
                is Result.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context,"Error en la carga de datos",Toast.LENGTH_SHORT).show()

                }
            }
        })

    }

}