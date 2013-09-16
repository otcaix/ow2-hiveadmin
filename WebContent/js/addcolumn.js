$(document).ready(
		function() {
			$('input#newColumn').click(
					function($e) {
						$e.preventDefault();
						var num = $('p#column_num').text();

						var id = 'cols_' + num;

						$('tr#cols_-1').clone(true).attr('id', id).removeAttr(
								"style").insertAfter('table#cols');
						var id1 = 'tr#' + id + '  #cols_name';
						var id2 = 'tr#' + id + '  #cols_type';
						var id3 = 'tr#' + id + '  #cols_comment';
						var name1 = 'columns[' + num + '].cols_name';
						var name2 = 'columns[' + num + '].cols_type';
						var name3 = 'columns[' + num + '].cols_comment';
						$(id1).attr('name', name1);
						$(id2).attr('name', name2);
						$(id3).attr('name', name3);
						num = parseInt(num) + 1;
						$('p#column_num').text(num);
					});
			$('input#removeColumn').click(function($e) {
				$e.preventDefault();
				var num = $('p#column_num').text();

				num = num - 1;
				if (num >= 1) {
					var id = 'tr#cols_' + num;
					$(id).remove();
					$('p#column_num').text(num);
				}
			});
			$('input#newP_Column').click(
					function($e) {
						$e.preventDefault();
						var num = $('p#P_column_num').text();

						var id = 'P_cols_' + num;

						$('tr#P_cols_-1').clone(true).attr('id', id)
								.removeAttr("style").insertAfter('table#P_cols');
						var id1 = 'tr#' + id + '  #cols_name';
						var id2 = 'tr#' + id + '  #cols_type';
						var id3 = 'tr#' + id + '  #cols_comment';

						var name1 = 'P_columns[' + num + '].cols_name';
						var name2 = 'P_columns[' + num + '].cols_type';
						var name3 = 'P_columns[' + num + '].cols_comment';
						$(id1).attr('name', name1);
						$(id2).attr('name', name2);
						$(id3).attr('name', name3);
						num = parseInt(num) + 1;
						$('p#P_column_num').text(num);
					});
		  	$('input#removeP_Column').click(function($e) {
				$e.preventDefault();
				var num = $('p#P_column_num').text();

				num = num - 1;
				if (num >= 0) {
					var id = 'tr#P_cols_' + num;
					$(id).remove();
					$('p#P_column_num').text(num);
				}
			});
		  	
			$('input#newpartition_spec').click(
					function($e) {
						$e.preventDefault();
						var num = $('p#partition_spec_num').text();

						var id = 'partition_spec_cols_' + num;

						$('div#partition_spec_cols_-1').clone(true).attr('id', id)
								.removeAttr("style").insertAfter('div#P_cols');
						var id1 = 'div#' + id + '  #partition_spec_name';
						var id2 = 'div#' + id + '  #partition_spec_value';
						

						var name1 = 'partition_spec[' + num + '].cols_name';
						var name2 = 'partition_spec[' + num + '].cols_type';
					
						$(id1).attr('name', name1);
						$(id2).attr('name', name2);
						
						num = parseInt(num) + 1;
						$('p#partition_spec_num').text(num);
					});
		  	$('input#removepartition_spec').click(function($e) {
				$e.preventDefault();
				var num = $('p#partition_spec_num').text();

				num = num - 1;
				if (num >= 1) {
					var id = 'div#partition_spec_cols_' + num;
					$(id).remove();
					$('p#partition_spec_num').text(num);
				}
				
			});
		  	
			$('input#add_partition').click(
					function($e) {
						$e.preventDefault();
						var num = $('p#partition_spec_num').text();

						var id = 'partition_spec_cols_' + num;

						$('div#partition_spec_cols_-1').clone(true).attr('id', id)
								.removeAttr("style").insertAfter('div#P_cols');
						var id1 = 'div#' + id + '  #partition_spec_name';
						var id2 = 'div#' + id + '  #partition_spec_value';
						

						var name1 = 'partition_spec[' + num + '].cols_name';
						var name2 = 'partition_spec[' + num + '].cols_type';
					
						$(id1).attr('name', name1);
						$(id2).attr('name', name2);
						
						num = parseInt(num) + 1;
						$('p#partition_spec_num').text(num);
					});
		  	$('input#remove_partition').click(function($e) {
				$e.preventDefault();
				var num = $('p#partition_spec_num').text();
				num=num-1;

				if (num >=0) {
					var id = 'div#partition_spec_cols_' + num;
					$(id).remove();
					$('p#partition_spec_num').text(num);
				}
				
			});

		});