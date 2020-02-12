package app.cermatitakehome.views.adapters

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import app.cermatitakehome.R
import app.cermatitakehome.models.GithubUserSearchItemModel
import com.squareup.picasso.Picasso

class GithubUserRecyclerAdapter(
    private val context : Context,
    private val github_users: List<GithubUserSearchItemModel>
) : RecyclerView.Adapter<GithubUserRecyclerAdapter.ViewHolder>() {
    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.github_user_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = github_users.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val github_user = github_users[position]
        holder.textUsername?.text = github_user.username
        Picasso.get()
            .load(Uri.parse(github_user.avatar))
            .placeholder(R.drawable.ic_terrain_grey_24dp)
            .error(R.drawable.ic_terrain_black_24dp)
            .into(holder.imageAvatar)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textUsername = itemView?.findViewById<TextView>(R.id.githubUsername)
        val imageAvatar = itemView?.findViewById<ImageView>(R.id.githubUserImage)
    }
}