class WishFetcher {
    companion object {
        val names = mutableListOf<String>()
        val prices = mutableListOf<String>()
        val links = mutableListOf<String>()

        fun getWishes(): MutableList<Wish> {
            var wishes : MutableList<Wish> = ArrayList()

            for (i in 0..(names.size-1)) {
                val wish = Wish(names[i], prices[i], links[i])
                wishes.add(wish)
            }
            return wishes
        }

        fun addWish(name: String, price: String, link: String) {
            names.add(name)
            prices.add(price)
            links.add(link)
        }
    }
}