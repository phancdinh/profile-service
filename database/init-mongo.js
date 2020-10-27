db.createUser(
  {
    user: "htuser",
    pwd: "hungthinh123",
    roles:[
      {
        role: "readWrite", db: "profile_db"
      }
    ]
  }
)
