package com.jumpstopstudios.swiper

import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * Note - NEVER USE PARAMETERS IN A FRAGMENT CONSTRUCTOR!
 * Here's why:
 *
 * When an activity is recreated (like after a phone-orientation change
 * or toggling night-mode), its fragments are automatically rebuilt.
 * The source code that rebuilds them uses reflection to instantiate
 * the fragment, and assumes that there is a constructor with no parameters.
 *
 * If your fragment only has a parameterized constructor, this will
 * cause your program to CRASH when the activity is recreated.
 *
 * If your fragment has both a default constructor and a parameterized
 * constructor, it will use the default one. This means the instantiated fragment
 * will have no access to any values/data you pass in via parameters.
 *
 * Solution:
 * Stick to the default constructor with no parameters, and instead of parameters,
 * pass in data through an arguments Bundle via a static instancing method like below.
 * This bundle is retained through fragment rebuilds.
 */
class PageFragment : Fragment() {

    companion object {
        /**
         * Static method for creating a PageFragment with arguments.
         */
        fun newInstance(index: Int): PageFragment {
            val fragment = PageFragment()

            val args = Bundle()
            args.putInt("index", index)
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_page, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.page_title).text = getString(R.string.page_title, arguments?.getInt("index"))
    }
}