package io.github.zacklukem.bells

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.design.widget.TabLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.view.*

import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView

import java.util.Calendar

class ScheduleView : AppCompatActivity() {

    /**
     * The [android.support.v4.view.PagerAdapter] that will provide
     * fragments for each of the sections. We use a
     * [FragmentPagerAdapter] derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * [android.support.v4.app.FragmentStatePagerAdapter].
     */
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    /**
     * The [ViewPager] that will host the section contents.
     */
    private var mViewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_view)

        //        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById<View>(R.id.container) as ViewPager
        mViewPager!!.adapter = mSectionsPagerAdapter

        val tabLayout = findViewById<View>(R.id.tabs) as TabLayout

        mViewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(mViewPager))


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_schedule_view, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    class PlaceholderFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_schedule_view, container, false)
            val day = arguments!!.getInt(ARG_SECTION_NUMBER)
            var today: Array<Schedule.Block>? = null
            when (day) {
                Calendar.MONDAY -> today = MainActivity.schedule.monday
                Calendar.TUESDAY -> today = MainActivity.schedule.tuesday
                Calendar.WEDNESDAY -> today = MainActivity.schedule.wednesday
                Calendar.THURSDAY -> today = MainActivity.schedule.thursday
                Calendar.FRIDAY -> today = MainActivity.schedule.friday
                else -> throw Error("Day of the week is broken :(")
            }
            val layout = rootView.findViewById<View>(R.id.tableLayout) as TableLayout
            for (block in today) {
                val row = TableRow(activity)

                val halfPad = 20
                val name = TextView(activity)
                name.text = block.name
                name.textSize = 18f
                name.gravity = Gravity.START
                name.setPadding(halfPad, 0, halfPad, 0)
                val start = TextView(activity)
                start.text = block.start.toString()
                start.textSize = 18f
                start.gravity = Gravity.CENTER
                start.setPadding(halfPad, 0, halfPad, 0)
                val end = TextView(activity)
                end.text = block.end.toString()
                end.textSize = 18f

                end.setPadding(halfPad, 0, halfPad, 0)



                row.addView(name)
                row.addView(start)
                row.addView(end)

                row.setPadding(20, 20, 0, 10)

                layout.addView(row)
            }
            //            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            return rootView
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 2)
        }


        override fun getCount(): Int {
            // Show 3 total pages.
            return 5
        }
    }
}
