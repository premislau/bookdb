using System;
using bookdb.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace bookdb.Controllers;

[ApiController]
[Route("authors")]
public class AuthorController: ControllerBase
{
	private readonly BookdbContext _context;
	public AuthorController(BookdbContext context)
	{
		_context = context;
	}
	
	[HttpGet]
	public async Task<ActionResult<IEnumerable<Author>>> GetAll(){
		return await _context.Authors.ToListAsync();
	}

	[HttpGet("{id}")]
	public async Task<ActionResult> Get(Guid id){
		Author? author = await _context.Authors.FindAsync(id);
		if(null == author){
			return NotFound(new { Message = "Author not found" });
		}
		return Ok(author);
	}

	[HttpPost]
	public async Task<ActionResult> Create([FromBody] Author author){
		_context.Authors.Add(author);
		await _context.SaveChangesAsync();
		//return CreatedAtAction(nameof(Author), new {id = author.Id}, author);
		return Ok(author);
	}

	[HttpDelete("{id}")]
	public async Task<IActionResult> DeleteMyEntity(int id){
		var author = await _context.Authors.FindAsync(id);
		if (author == null)
		{
			return NotFound();
		}

		_context.Authors.Remove(author);
		await _context.SaveChangesAsync();

		return NoContent();
	}

	[HttpGet("{id}/books")]
	public async Task<ActionResult<IEnumerable<Book>>> GetBooksByAuthor(Guid id){
		var author = await _context.Authors
			.Include(a => a.Books)
			.FirstOrDefaultAsync(a => a.Id == id);

		if (author == null)
		{
			return NotFound();
		}

		return Ok(author.Books);
	}

	[HttpPost("{authorId}/books")]
	public async Task<ActionResult> AddMultipleBooks(Guid authorId, [FromBody] List<Guid> bookIds){
		var author = await _context.Authors
			.Include(a => a.Books)
			.FirstOrDefaultAsync(a => a.Id == authorId);
		
		if (author == null)
		{
			return NotFound(new { Message = "Author not found" });
		}
		List<Book> booksToAdd = new List<Book>(bookIds.Count);
		foreach(Guid bookId in bookIds){
			Book? book =  await _context.Books.FindAsync(bookId);			
			if(null == book){
				return BadRequest(new {Message=$"There is no book with id {bookId} in the database"});
			}
			booksToAdd.Add(book);
		}

		//books are added only if all bookIds are present in the database
		foreach (var book in booksToAdd)
		{
			author.Books.Add(book);
		}
		await _context.SaveChangesAsync();

		return Ok(author);
	}

}
