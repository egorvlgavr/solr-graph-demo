import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders title', () => {
  const { getByText } = render(<App />);
  const h3Element = getByText(/Solr categories graph/i);
  expect(h3Element).toBeInTheDocument();
});
