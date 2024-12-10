using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Text.Json.Serialization;

namespace bookdb.Models;

public partial class Book
{
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    [Key]
    public Guid Id { get; set; }

    public string? Title { get; set; }

    public DateOnly? PublicationDate { get; set; }

    [JsonIgnore]
    public virtual ICollection<Author> Authors { get; set; } = new HashSet<Author>();
}
